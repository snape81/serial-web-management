package com.nexse.serial.server.rxtx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;


public class SerialPortReaderThread implements Runnable {
    static final Logger logger = LoggerFactory.getLogger(SerialPortReaderThread.class);

    InputStream in;

    public SerialPortReaderThread(InputStream in) {
        this.in = in;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        try {
            while ((len = this.in.read(buffer)) > -1) {
                sb.append(new String(buffer, 0, len));
            }
            logger.debug("Stringa letta dallo scannner {}", sb.toString());
        } catch (IOException e) {
            logger.error("Errore nella lettura dello stream dalla seriale ", e);
        }
    }
}

