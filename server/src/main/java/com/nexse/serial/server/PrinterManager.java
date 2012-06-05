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
    private EventIntExchange printerCommand;

    public PrinterManager(String printerDevId, int printerPortBaud, int printerDataBit, int printerStopBit, int printerParity,   EventIntExchange printerCommand) throws Exception {
        this.printerDevId = printerDevId;
        this.printerCommand = printerCommand;
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


    public synchronized void printAWebsocketMessage(String toWrite) {
        addLogo();

        for (byte b : toWrite.getBytes()) {
            logger.debug("Ready to print char {} --> from byte {} ", (char) b, (int) b);
            printerCommand.put((int) b);
        }
        sendPrintAndCut();

    }

    private void sendPrintAndCut() {
        printerCommand.put(PrinterCommands.PRINT_COMMAND);
        printerCommand.put(PrinterCommands.FULL_KNIFE_CUT_COMMAND);

    }



    private void addLogo() {

        for (byte b : PrinterCommands.LOGO_NEXSE) {
            printerCommand.put((int)b);
        }
    }

    public void startPrinterLoopFromWebsocket(EventStringExchange dataToPrintFromWebsocket) {
         new Thread(new MainPrinterLoop(dataToPrintFromWebsocket)).start();
    }


    private class MainPrinterLoop implements Runnable {


        private EventStringExchange dataToPrintFromWebsocket;

        private MainPrinterLoop( EventStringExchange dataToPrintFromWebsocket) {
            this.dataToPrintFromWebsocket = dataToPrintFromWebsocket;
        }

        @Override
        public void run() {
            while (true) {
                logger.debug("In attesa di una stringa da stampare .... ");
                // ciclo infinito di attesa di ricezione dati
                String mess = dataToPrintFromWebsocket.get();
                logger.debug(" PRINTER deve stampare {}", mess);
                printAWebsocketMessage(mess);
                logger.debug(" ready for next printing !");


            }
        }
    }
}
