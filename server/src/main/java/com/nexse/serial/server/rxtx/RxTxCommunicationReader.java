package com.nexse.serial.server.rxtx;

import com.nexse.serial.conf.ScannerCommands;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.*;

public class RxTxCommunicationReader {

    public RxTxCommunicationReader() {
        super();
    }

    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                 SerialWriter serWriter = new SerialWriter(out);
                 Thread writer  = new Thread(serWriter);
                writer.start();
                (new Thread(new SerialReader(in,serWriter))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    /** */
    public static class SerialReader implements Runnable
    {
        InputStream in;
        SerialWriter writer;

        public SerialReader(InputStream in, SerialWriter writer)
        {
            this.in = in;
            this.writer = writer;
        }


        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            StringBuffer sb = new StringBuffer();
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    //System.out.print(new String(buffer,0,len));

                    System.out.println("---> "+new String(buffer,0,len));
                    System.out.println("len   " + len);
                    sb.append(new String(buffer,0,len));
                    for (int i = 0 ; i < len ; i++) {
                        System.out.println("READ VAL: " + buffer[i]);
                        if (buffer[i] != 0) {
                            System.out.println("CR? " + (buffer[i] == 13));
                            if ((buffer[i] == 13)) {
                                System.out.println(" *********************** LETTO TOTALE " + sb.toString());
                                sb = new StringBuffer();
                                writer.putInBadTray();


                            }
                            System.out.println("LF? " + (buffer[i] == 10));
                        }
                    }
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    /** */
    public static class SerialWriter implements Runnable
    {
        OutputStream out;

        public SerialWriter ( OutputStream out)
        {
            this.out = out;
        }

        public void putInGoodTray() {

            try {
                this.out.write(47);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void putInBadTray() {
            try {
                System.out.println(" requested to put in bad tray ..... ");
                           this.out.write(ScannerCommands.getIntValueOfCommand(ScannerCommands.COMMAND_G));
                System.out.println("send to scanner int 53 (hex) --> 83 DEC");
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
        }


        public void run ()
        {
            try
            {
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public static void main ( String[] args )
    {
        try
        {
            (new RxTxCommunicationReader()).connect("/dev/ttyUSB0");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}