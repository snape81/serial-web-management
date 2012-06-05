package com.nexse.serial;

import com.nexse.serial.server.PrinterManager;
import com.nexse.serial.server.ScannerManager;
import com.nexse.serial.server.exchange.EventIntExchange;
import com.nexse.serial.server.exchange.EventMarkSenseExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import com.nexse.serial.server.websocket.ScannerWebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static com.nexse.serial.server.conf.ConfigConstants.*;

public class MainServer {
    static final Logger logger = LoggerFactory.getLogger(MainServer.class);


    public static void main(String[] args) {
        logger.info(" ------------- SERIAL PORT SERVER START -------------------- ");

        EventIntExchange scannerCommand = new EventIntExchange();
        EventIntExchange printerData = new EventIntExchange();

        EventStringExchange scannerData = new EventStringExchange();
        EventStringExchange printerDataFromWebSocket = new EventStringExchange();

        EventMarkSenseExchange scannerDataToWebSocket = new EventMarkSenseExchange();

        ScannerManager scannerManager = null;
        PrinterManager printerManager = null;

        // LETTURA DELLE PROPERTIES DA FILE
        logger.debug(" starting reading properties from file  ... " + PORTS_PROPERTIES_FILE_NAME);
        Properties portProperties = new Properties();
        try {
            portProperties.load(ClassLoader.class.getResourceAsStream("/" + PORTS_PROPERTIES_FILE_NAME));
            logger.info(" PORT PROPERTIES READING DONE: {}", portProperties);

        } catch (Exception e) {
            logger.error(" FATAL --- error in reading serial ports properties  ", e);
            System.exit(2);
        }

        // CREAZIONE DELLA CLASSE GESTORE DELLO SCANNER
        try {
            logger.debug(" start instantiate rxtx scanner utility ---> SCANNER ");
            scannerManager = new ScannerManager(portProperties.getProperty(SCANNER_PORT_DEVICE_ID),
                    new Integer(portProperties.getProperty(SCANNER_PORT_BAUD_RATE)),
                    new Integer(portProperties.getProperty(SCANNER_PORT_DATA_BIT)),
                    new Integer(portProperties.getProperty(SCANNER_PORT_STOP_BIT)),
                    new Integer(portProperties.getProperty(SCANNER_PORT_PARITY)));
            logger.debug("  instantiate rxtx scanner utility completed ---> SCANNER");

            logger.debug(" start instantiate rxtx printer utility ---> PRINTER");
            printerManager = new PrinterManager(portProperties.getProperty(PRINTER_PORT_DEVICE_ID),
                    new Integer((String) portProperties.get(PRINTER_PORT_BAUD_RATE)),
                    new Integer((String) portProperties.get(PRINTER_PORT_DATA_BIT)),
                    new Integer((String) portProperties.get(PRINTER_PORT_STOP_BIT)),
                    new Integer((String) portProperties.get(PRINTER_PORT_PARITY)),printerData);
            logger.debug("  instantiate rxtx scanner utility completed ---> PRINTER");


            logger.debug(" start open connection to scanner  ....  ");
            scannerManager.startConnectionToScanner(scannerCommand);
            logger.debug(" connection to scanner opened ");

            logger.debug(" start open connection from scanner  ....  ");
            scannerManager.startConnectionFromScanner(scannerData);
            logger.debug(" connection from scanner opened ");
            logger.debug("init bitmask fashion reading ... ");
            scannerManager.initScannerWithBitmaskReading(scannerCommand);
            logger.debug(" ... reding initialized");

            ScannerWebSocketServer swss = new ScannerWebSocketServer(new Integer( portProperties.getProperty(WEBSOCKET_PORT)),scannerDataToWebSocket,printerDataFromWebSocket);
            swss.run();

            logger.debug("start loop scanner reading  .... ");
            scannerManager.startReaderLoopFromScanner(scannerCommand,scannerData,scannerDataToWebSocket);
            logger.debug("loop scanner reading started!");


            logger.debug(" start open connection to printer  ....  ");
            printerManager.startConnectionToPrinter(printerData);
            logger.debug(" connection to scanner printer ");

            logger.debug("start loop printer  .... ");
            printerManager.startPrinterLoopFromWebsocket(printerDataFromWebSocket);
                        logger.debug("loop printer started!");



        } catch (Exception e) {
            logger.error(" errore nell'apertura del manager dello scanner ", e);
            System.exit(3);
        }


    }




}
