package com.nexse.serial.server;

import com.nexse.serial.server.conf.PrinterCommands;
import com.nexse.serial.server.exchange.EventIntExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import com.nexse.serial.server.rxtx.PrinterSerialPortWriterThread;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PrinterManager {
    static final Logger logger = LoggerFactory.getLogger(PrinterManager.class);

    private SerialPort printerSerialPort;
    private String printerDevId;

    public PrinterManager(String printerDevId, int printerPortBaud, int printerDataBit, int printerStopBit, int printerParity) throws Exception {
        this.printerDevId = printerDevId;

        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(printerDevId);
        if (portIdentifier.isCurrentlyOwned()) {
            logger.error("Error: Port " + printerDevId + " is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                printerSerialPort = (SerialPort) commPort;
                printerSerialPort.setSerialPortParams(printerPortBaud, printerDataBit, printerStopBit, printerParity);
                printerSerialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            } else {
                logger.error("Error: Only serial ports handled.");
                throw new IOException("Port " + printerDevId + " is not a serial port! check your configuration ");
            }
        }
    }


    public void startConnectionToPrinter(EventIntExchange commandToSend) throws IOException {
        logger.debug(" Starting writing thread .... ");
        new Thread(new PrinterSerialPortWriterThread(printerDevId, printerSerialPort.getOutputStream(), commandToSend)).start();
        logger.debug(" Writing thread started .... ");
    }


    public synchronized void printAString(String toWrite, EventIntExchange command) {
        for (byte b : toWrite.getBytes()) {
            logger.debug("Ready to print char {} --> from byte {} ", (char) b, (int) b);
            command.put((int) b);
        }
        sendPrintAndCut(command);
    }

    private void sendPrintAndCut(EventIntExchange command) {
        command.put(PrinterCommands.PRINT_COMMAND);
        command.put(PrinterCommands.FULL_KNIFE_CUT_COMMAND);

    }

    public void startPrinterLoopFromWebsocket(EventIntExchange printerCommand, EventStringExchange dataToPrintFromWebsocket) {
         new Thread(new MainPrinterLoop(printerCommand,dataToPrintFromWebsocket)).start();
    }


    private class MainPrinterLoop implements Runnable {

        private EventIntExchange printerCommand;
        private EventStringExchange dataToPrintFromWebsocket;

        private MainPrinterLoop(EventIntExchange printerCommand, EventStringExchange dataToPrintFromWebsocket) {
            this.printerCommand = printerCommand;
            this.dataToPrintFromWebsocket = dataToPrintFromWebsocket;
        }

        @Override
        public void run() {
            while (true) {
                logger.debug("In attesa di una stringa da stampare .... ");
                // ciclo infinito di attesa di ricezione dati
                String mess = dataToPrintFromWebsocket.get();
                logger.debug(" PRINTER deve stampare {}", mess);
                printAString(mess, printerCommand);
                logger.debug(" ready for next printing !");


            }
        }
    }
}
