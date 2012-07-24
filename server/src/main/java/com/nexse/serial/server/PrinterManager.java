package com.nexse.serial.server;

import com.nexse.serial.server.conf.PrinterCommands;
import com.nexse.serial.server.exchange.EventIntArraylistExchange;
import com.nexse.serial.server.exchange.EventIntExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import com.nexse.serial.server.rxtx.PrinterSerialPortWriterThread;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
        centerJustification();
        addLogo();
        leftJustification();


        for (byte b : toWrite.getBytes()) {
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

    private void centerJustification() {
        for (byte b : new byte[]{0x1B, 0x61, 0x1}) {
            printerCommand.put((int)b);
        }
    }

    private void feedNLines(int lineNumber) {
       printerCommand.put(0x14);
       printerCommand.put(lineNumber);
    }

    private void leftJustification() {
            for (byte b : new byte[]{0x1B, 0x61, 0x0}) {
                printerCommand.put((int)b);
            }
        }

    public void startPrinterLoopFromWebsocket(EventStringExchange dataToPrintFromWebsocket) {
         new Thread(new MainPrinterLoop(dataToPrintFromWebsocket)).start();
    }

    public void startSportwettePrinterLoopFromWebsocket(EventIntArraylistExchange exchange) {
            new Thread(new SportwettePrinterLoop(exchange)).start();
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

    private class SportwettePrinterLoop implements Runnable {


            private EventIntArraylistExchange dataToPrintFromPrinterFacility;

            private SportwettePrinterLoop( EventIntArraylistExchange dataToPrintFromPrinterFacility) {
                this.dataToPrintFromPrinterFacility = dataToPrintFromPrinterFacility;
            }

            @Override
            public void run() {
                while (true) {
                    logger.debug("In attesa di una schedina  da stampare .... ");
                    // ciclo infinito di attesa di ricezione dati
                    ArrayList<Integer> mess = dataToPrintFromPrinterFacility.get();
                    logger.debug(" PRINTER deve stampare num byte {}", mess.size());
                    printArray(mess);
                    logger.debug(" ready for next printing !");


                }
            }
        }

    public void loadLogoNexseFromReasources(int numLogo,String filename) {
           InputStream inLogo = ClassLoader.getSystemResourceAsStream(filename);
           logger.debug("IN logo  input stream {}",inLogo);
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        try {
            while ((len = inLogo.read(buffer)) > -1) {
             baos.write(buffer,0,len);
            }
        } catch (IOException e) {
          logger.error("errore caricamento logo ");
        }

        printerCommand.put(0x1D);
        printerCommand.put(0x23);
        printerCommand.put(numLogo);
        printerCommand.put(0x1B);
        for (byte b : baos.toByteArray()) {
            printerCommand.put((int)b);
        }

       }

    public void printArray(ArrayList<Integer> toPrint) {
        for (int integer : toPrint) {
           printerCommand.put(integer);
        }
    }
}
