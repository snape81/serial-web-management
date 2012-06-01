package com.nexse.serial.server;

import com.nexse.serial.server.exchange.EventIntExchange;
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
        new Thread(new SerialPortWriterThread(scannerDevId,scannerSerialPort.getOutputStream(),commandToSend)).start();
        logger.debug(" Writing thread started .... ");
    }

    public void startConnectionFromScanner(EventStringExchange dataToRead) throws IOException {
        logger.debug(" Starting reading thread .... ");
        new Thread(new SerialPortReaderThread(scannerDevId,scannerSerialPort.getInputStream(),dataToRead)).start();
                logger.debug(" Reading thread started .... ");
    }


}
