package com.nexse.serial;

import com.nexse.serial.server.conf.ScannerCommands;
import com.nexse.serial.server.MarkSenseCard;
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
        EventStringExchange scannerData = new EventStringExchange();
        EventMarkSenseExchange scannerDataToWebSocket = new EventMarkSenseExchange();
        ScannerManager scannerManager = null;

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
            logger.debug(" start instantiate rxtx scanner utility ");
            scannerManager = new ScannerManager(portProperties.getProperty(SCANNER_PORT_DEVICE_ID),
                    new Integer((String) portProperties.get(SCANNER_PORT_BAUD_RATE)),
                    new Integer((String) portProperties.get(SCANNER_PORT_DATA_BIT)),
                    new Integer((String) portProperties.get(SCANNER_PORT_STOP_BIT)),
                    new Integer((String) portProperties.get(SCANNER_PORT_PARITY)));

            logger.debug("  instantiate rxtx scanner utility completed ");
            // CREAZIONE PIPELINE DI INVIO COMANDI E INIZIALIZZAZIONE ALLA LETTURA "L"
            logger.debug(" start open connection to scanner  ....  ");
            scannerManager.startConnectionToScanner(scannerCommand);
            logger.debug(" connection to scanner opened ");

            //logger.debug("SEND L Command ");
            scannerCommand.put(ScannerCommands.getIntValueOfCommand(ScannerCommands.COMMAND_L));

            // CREAZIONE PIPELINE DI RICEZIONE DATI
            logger.debug(" start open connection from scanner  ....  ");
            scannerManager.startConnectionFromScanner(scannerData);
            logger.debug(" connection from scanner opened ");

            //todo websocket port in prop file
            ScannerWebSocketServer swss = new ScannerWebSocketServer(8080,scannerDataToWebSocket);
            swss.run();


            while (true) {
                // ciclo infinito di attesa di ricezione dati
                //todo websocket
                String mess = scannerData.get();
                logger.debug(" SCANNER READER ha letto {}",mess);
                MarkSenseCard msc = validaLetturaECreaSchedina(mess);
                logger.debug(" msc validata {} la spedisco alla websocket " , msc );
                scannerDataToWebSocket.put(msc);

                logger.debug(" Ejecting in bad tray .... ");
                scannerCommand.put(ScannerCommands.getIntValueOfCommand(ScannerCommands.COMMAND_S));
                logger.debug(" ready for next reading !");



            }


        } catch (Exception e) {
            logger.error(" errore nell'apertura del manager dello scanner ", e);
            System.exit(3);
        }


    }

    private static MarkSenseCard validaLetturaECreaSchedina(String rawDataFromScanner) {
           MarkSenseCard msc = new MarkSenseCard();


           msc.setRawString(rawDataFromScanner);
           return msc;
    }


}
