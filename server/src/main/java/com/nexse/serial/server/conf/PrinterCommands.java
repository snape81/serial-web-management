package com.nexse.serial.server.conf;

public class PrinterCommands {
    public static final byte[] LOGO_NEXSE = new byte[]{0x1D,0x23,0x4,0x1D,0x2F,0x00};

    public static final int PRINT_COMMAND = 0x17;
    public static final int FULL_KNIFE_CUT_COMMAND = 0x19;

    public static final int PRINT_AND_ONE_LINE_FEED = 0x0A ;
}
