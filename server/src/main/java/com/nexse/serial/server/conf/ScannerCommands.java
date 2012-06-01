package com.nexse.serial.server.conf;

public class ScannerCommands {

    // Two side card  (after L command)
    public static final String COMMAND_D = "44";

    // Card read by bitmask {XX}{0-3}{0-F}{0-F}{0-F} -- CR alla fine
    public static final String COMMAND_L = "4C";

    // Send card in bad tray
    public static final String COMMAND_S = "53";

    // Send card in good tray
    public static final String COMMAND_G = "47";

    //Resend last read data
    public static final String COMMAND_W = "57";

    //ascii CR int value
    public static final int CR_INT_VALUE = 13;


    public static int getIntValueOfCommand(String command) {
        return Integer.parseInt(command, 16);
    }

}
