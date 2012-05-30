package com.nexse.serial;

import com.nexse.serial.server.rxtx.SerialPortReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static com.nexse.serial.conf.ConfigConstants.*;

public class MainServer {
    static final Logger logger = LoggerFactory.getLogger(MainServer.class);


    public static void main(String[] args) {
       logger.info(" ------------- SERIAL PORT SERVER START -------------------- ");


        logger.debug(" starting reading properties from file  ... " + PORTS_PROPERTIES_FILE_NAME);
        Properties portProperties = new Properties();
        try {
            portProperties.load(ClassLoader.class.getResourceAsStream("/" + PORTS_PROPERTIES_FILE_NAME));
            logger.info(" PORT PROPERTIES READING DONE: {}", portProperties);

        } catch (Exception e) {
            logger.error(" FATAL --- error in reading serial ports properties  ",e);
            return;
        }

        logger.debug(" start instantiate rxtx scanner reader utility ");

        try{
            (new SerialPortReader()).connect(portProperties.getProperty(SCANNER_PORT_DEVICE_ID),new Integer((String)portProperties.get(SCANNER_PORT_BAUD_RATE)),new Integer((String) portProperties.get(SCANNER_PORT_PARITY)));

        } catch (Exception e) {
            logger.error(" errore nell'apertura del reader dello scanner ",e);


        }




    }

}
