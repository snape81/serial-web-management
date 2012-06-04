package com.nexse.serial.server.rxtx;

import com.nexse.serial.server.exchange.EventIntExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

public class PrinterSerialPortWriterThread implements Runnable {
    static final Logger logger = LoggerFactory.getLogger(PrinterSerialPortWriterThread.class);

    OutputStream out;
    EventIntExchange commandExchange;
    String portId;

    public PrinterSerialPortWriterThread(String portId, OutputStream out, EventIntExchange commandExchange) {
        this.out = out;
        this.commandExchange = commandExchange;
        this.portId = portId;
    }

    public void run() {
        try {
            while (true) {
                int commandToSend = commandExchange.get();
                this.out.write(commandToSend);
            }
        } catch (IOException e) {
           logger.error("Error in writing command on port " + portId,e);
        }
    }
}
