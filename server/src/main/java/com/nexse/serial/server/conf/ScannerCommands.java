package com.nexse.serial.server.conf;

public class ScannerCommands {

    // Two side card  (after L command)
    public static final int READ_TOWO_SIDE_CARD_D = 0x44;

    // Card read by bitmask {XX}{0-3}{0-F}{0-F}{0-F} -- CR alla fine
    public static final int READ_BITMASK_FASHION_L = 0x4C;

    // Send card in bad tray
    public static final int EJECT_IN_BAD_TRAY_S = 0x53;

    // Send card in good tray
    public static final int EJECT_IN_GOOD_TRAY_G = 0x47;

    //Resend last read data
    public static final int RESEND_LAST_READ_DATA_W = 0x57;

    //ascii CR int value
    public static final int CR_INT_VALUE = 13;




}
