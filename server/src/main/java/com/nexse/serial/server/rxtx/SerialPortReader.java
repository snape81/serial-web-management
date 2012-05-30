package com.nexse.serial.server.rxtx;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class SerialPortReader {
    static final Logger logger = LoggerFactory.getLogger(SerialPortReader.class);

    public SerialPortReader() {
        super();
    }


    public void connect(String portName,int baudrate,int parity) throws Exception {

            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            if (portIdentifier.isCurrentlyOwned()) {
                logger.error("Error: Port " + portName + " is currently in use");
            } else {
                CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

                if (commPort instanceof SerialPort) {
                    SerialPort serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, parity);

                    InputStream in = serialPort.getInputStream();


                    (new Thread(new SerialReader(in))).start();

                } else {
                    logger.error("Error: Only serial ports are handled.");
                }
            }
        }



    public static class SerialReader implements Runnable {
            InputStream in;

            public SerialReader(InputStream in) {
                logger.debug("PLUTO");
                this.in = in;
            }

            public void run() {
                logger.debug("PAPERINO");
                byte[] buffer = new byte[1024];
                int len = -1;
                StringBuilder sb = new StringBuilder();
                try {
                    while ((len = this.in.read(buffer)) > -1) {
                        logger.debug("PIPPO");
                        sb.append(new String(buffer, 0, len));
                        logger.debug("PIPPO2 " + sb.toString());
                    }
                    logger.debug("Stringa letta dallo scannner {}",sb.toString());
                } catch (IOException e) {
                    logger.error("Errore nella lettura dello stream dalla seriale ",e);
                }
            }
        }
}
