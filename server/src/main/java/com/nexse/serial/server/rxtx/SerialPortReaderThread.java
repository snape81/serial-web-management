package com.nexse.serial.server.rxtx;

import com.nexse.serial.conf.ScannerCommands;
import com.nexse.serial.server.exchange.EventStringExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;


public class SerialPortReaderThread implements Runnable {
    static final Logger logger = LoggerFactory.getLogger(SerialPortReaderThread.class);

    String portId;
    InputStream in;
    EventStringExchange dataExchange;

    public SerialPortReaderThread(String portId, InputStream in, EventStringExchange dataExchange) {
        this.portId = portId;
        this.in = in;
        this.dataExchange = dataExchange;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        try {
            while ((len = this.in.read(buffer)) > -1) {
                for (int i = 0; i < len; i++) {

                    if (buffer[i] == ScannerCommands.CR_INT_VALUE) {
                        String raw = sb.toString();
                        dataExchange.put(raw);
                        sb = new StringBuilder();
                        break;
                    } else {
                        sb.append((char) buffer[i]);
                    }
                }

            }

        } catch (IOException e) {
            logger.error("Error in writing command on port " + portId, e);
        }
    }
}

