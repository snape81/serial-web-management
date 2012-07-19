package com.nexse.serial.server;

import com.nexse.serial.server.bean.MarkSenseCard;
import com.nexse.serial.server.bean.MarkSenseCardFactory;
import com.nexse.serial.server.conf.ScannerCommands;
import com.nexse.serial.server.exchange.EventIntExchange;
import com.nexse.serial.server.exchange.EventMarkSenseExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import com.nexse.serial.server.rxtx.SerialPortReaderThread;
import com.nexse.serial.server.rxtx.SerialPortWriterThread;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ScannerManager {
    static final Logger logger = LoggerFactory.getLogger(ScannerManager.class);

    private SerialPort scannerSerialPort;
    private String scannerDevId;

    public ScannerManager(String scannerDevId, int scannerPortBaud, int scannerDataBit, int scannerStopBit, int scannerParity) throws Exception {
        this.scannerDevId = scannerDevId;
        logger.info("INIT DEV ID: {}",scannerDevId);
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(scannerDevId);
        if (portIdentifier.isCurrentlyOwned()) {
            logger.error("Error: Port " + scannerDevId + " is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                scannerSerialPort = (SerialPort) commPort;
                scannerSerialPort.setSerialPortParams(scannerPortBaud, scannerDataBit, scannerStopBit, scannerParity);
                scannerSerialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            } else {
                logger.error("Error: Only serial ports handled.");
                throw new IOException("Port " + scannerDevId + " is not a serial port! check your configuration ");
            }
        }
    }


    public void startConnectionToScanner(EventIntExchange commandToSend) throws IOException {
        logger.debug(" Starting writing thread .... ");
        new Thread(new SerialPortWriterThread(scannerDevId, scannerSerialPort.getOutputStream(), commandToSend)).start();
        logger.debug(" Writing thread started .... ");
    }

    public void startConnectionFromScanner(EventStringExchange dataToRead) throws IOException {
        logger.debug(" Starting reading thread .... ");
        new Thread(new SerialPortReaderThread(scannerDevId, scannerSerialPort.getInputStream(), dataToRead)).start();
        logger.debug(" Reading thread started .... ");
    }


    public void startReaderLoopFromScanner(EventIntExchange scannerCommand, EventStringExchange scannerData, EventMarkSenseExchange scannerDataToWebSocket) {
           new Thread(new MainScannerLoop(scannerCommand,scannerData,scannerDataToWebSocket)).start();

    }

    private MarkSenseCard validaLetturaECreaSchedina(String rawDataFromScanner) {
        MarkSenseCard msc = MarkSenseCardFactory.getMscFromString(rawDataFromScanner);
        logger.debug("Mark sense generata {}",msc);
        return msc;
    }

    public void initScannerWithBitmaskReading(EventIntExchange command) {
        command.put(ScannerCommands.READ_BITMASK_FASHION_L);
    }

    private  class MainScannerLoop implements Runnable {

        private EventIntExchange scannerCommand;
        private EventStringExchange scannerData;
        private EventMarkSenseExchange scannerDataToWebSocket;

        private MainScannerLoop(EventIntExchange scannerCommand, EventStringExchange scannerData, EventMarkSenseExchange scannerDataToWebSocket) {
            this.scannerCommand = scannerCommand;
            this.scannerData = scannerData;
            this.scannerDataToWebSocket = scannerDataToWebSocket;
        }

        @Override
        public void run() {
            while (true) {
                // ciclo infinito di attesa di ricezione dati
                String mess = scannerData.get();
                logger.debug(" SCANNER READER ha letto {}", mess);
                MarkSenseCard msc = validaLetturaECreaSchedina(mess);
                scannerDataToWebSocket.put(msc);
                if (!msc.isValid()) {
                    logger.debug(" lettura errata dallo scanner", msc.getRow());
                    logger.debug(" Ejecting in bad tray .... ");
                    scannerCommand.put(ScannerCommands.EJECT_IN_BAD_TRAY_S);
                }

                if (msc.isEmpty()) {
                    logger.debug(" msc vuota ", msc.getRow());
                    logger.debug(" Ejecting in bad tray .... ");
                    scannerCommand.put(ScannerCommands.EJECT_IN_BAD_TRAY_S);
                } else {
                    logger.debug(" msc validata {} la spedisco alla websocket ", msc);
                    logger.debug(" Ejecting in good tray .... ");
                    scannerCommand.put(ScannerCommands.EJECT_IN_GOOD_TRAY_G);
                }

                logger.debug(" ready for next reading !");


            }
        }
    }
}
