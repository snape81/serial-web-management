package com.nexse.serial;

import com.nexse.serial.conf.ScannerCommands;
import com.nexse.serial.server.ScannerManager;
import com.nexse.serial.server.exchange.EventIntExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static com.nexse.serial.conf.ConfigConstants.*;

public class MainServer {
    static final Logger logger = LoggerFactory.getLogger(MainServer.class);


    public static void main(String[] args) {
       logger.info(" ------------- SERIAL PORT SERVER START -------------------- ");

        EventIntExchange scannerCommand = new EventIntExchange();
        EventStringExchange scannerData = new EventStringExchange();
        ScannerManager scannerManager = null;

         // LETTURA DELLE PROPERTIES DA FILE
        logger.debug(" starting reading properties from file  ... " + PORTS_PROPERTIES_FILE_NAME);
        Properties portProperties = new Properties();
        try {
            portProperties.load(ClassLoader.class.getResourceAsStream("/" + PORTS_PROPERTIES_FILE_NAME));
            logger.info(" PORT PROPERTIES READING DONE: {}", portProperties);

        } catch (Exception e) {
            logger.error(" FATAL --- error in reading serial ports properties  ",e);
            System.exit(2);
        }

        // CREAZIONE DELLA CLASSE GESTORE DELLO SCANNER
        try{
            logger.debug(" start instantiate rxtx scanner utility ");
            scannerManager = new ScannerManager(portProperties.getProperty(SCANNER_PORT_DEVICE_ID),
                                                    new Integer((String)portProperties.get(SCANNER_PORT_BAUD_RATE)),
                                                    new Integer((String)portProperties.get(SCANNER_PORT_DATA_BIT)),
                                                    new Integer((String)portProperties.get(SCANNER_PORT_STOP_BIT)),
                                                    new Integer((String)portProperties.get(SCANNER_PORT_PARITY)));

            logger.debug("  instantiate rxtx scanner utility completed ");
            // CREAZIONE PIPELINE DI INVIO COMANDI E INIZIALIZZAZIONE ALLA LETTURA "L"
            logger.debug(" start open connection to scanner  ....  ");
            scannerManager.startConnectionToScanner(scannerCommand);
            logger.debug(" connection to scanner opened ");

            logger.debug("SEND L Command ");
            scannerCommand.put(ScannerCommands.getIntValueOfCommand(ScannerCommands.COMMAND_L));

           // CREAZIONE PIPELINE DI INVIO COMANDI E INIZIALIZZAZIONE ALLA LETTURA "L"
                       logger.debug(" start open connection from scanner  ....  ");
                       scannerManager.startConnectionFromScanner(scannerData);
                       logger.debug(" connection from scanner opened ");


        } catch (Exception e) {
            logger.error(" errore nell'apertura del manager dello scanner ", e);
            System.exit(3);
        }





    }

}
