package com.nexse.serial.server;

import com.nexse.serial.server.exchange.EventIntExchange;

import java.util.ArrayList;
import java.util.Arrays;

public class BetslipFormatterTest {
    private static final int LINE_FEED = 10;
    private static final int LOGO_NEXSE_ID = 4;
    public static final int END_BARCODE_VALUE = 0;
    public static final int PRINT_AND_CUT = 25;
    public static final int EURO_INT = 213;

    public static final Integer[] INIT_PRINTER = new Integer[]{27,64};
    public static final Integer[] SELECT_INTERNATIONAL_CHARS = new Integer[]{27,82,6};
    public static final Integer[] SELECT_LOGO_6 = new Integer[]{29,35,6};
    public static final Integer[] PRINT_SELECTED_LOGO_IN_NORMAL_MODE = new Integer[]{29,47,0};
    public static final Integer[] LEFT_JUSTIFICATION = new Integer[]{27,97,48};
    public static final Integer[] CENTER_JUSTIFICATION = new Integer[]{27,97,49};
    public static final Integer[] RIGHT_JUSTIFICATION = new Integer[]{27,97,50};
    public static final Integer[] SET_RELATIVE_PRINTER_POS = new Integer[]{27,92,65,0};
    public static final Integer[] ENLARGE_FONTS = new Integer[]{29,33,33};
    public static final Integer[] RESET_FONTS_DIM = new Integer[]{29,33,0};
    public static final Integer[] SELECT_LOGO_BOLD_LINE  = new Integer[]{29,35,12};
    public static final Integer[] SELECT_LOGO_THIN_LINE  = new Integer[]{29,35,11};
    public static final Integer[] SELECT_LOGO_COMPANY  = new Integer[]{29,35,5};
    public static final Integer[] SELECT_LOGO_DISCLAIMER  = new Integer[]{29,35,20};
    public static final Integer[] FEED_6_DOT_ROWS = new Integer[]{21,6};
    public static final Integer[] FEED_3_DOT_ROWS = new Integer[]{21,3};
    public static final Integer[] BARCODE_UNKNOWN_COMMAND = new Integer[]{29,79,0};
    public static final Integer[] BARCODE_HEIGHT_TO_80 = new Integer[]{29,104,80};
    public static final Integer[] SET_PRINTER_AREA_WIDTH = new Integer[]{29,87,1,3};
    public static final Integer[] SET_BARCODE_WIDTH_TO_3 = new Integer[]{29,119,3};
    public static final Integer[] BARCODE_PREAMBLE = new Integer[]{29,107,2};

    
    private static ArrayList<Integer> dataToPrint;
    private static int betslipCounter = 1;

    private static final String COMPANY_NUM = "07";
    private static final String SHOP_NUM = "098";
    private static final String PROGRAM_IDENTIFIER ="  24/2012";


    public static void main(String[] args) throws Exception {

        printMarkSenseCard();
    }



    private static void printMarkSenseCard() throws Exception {
        EventIntExchange printerData = new EventIntExchange();

        PrinterManager printerManager = new PrinterManager("/dev/ttyUSB0", 115200, 8, 1, 0, printerData);
        printerManager.startConnectionToPrinter(printerData);


        dataToPrint = new ArrayList<>();

        addToDataToPrint(INIT_PRINTER);
        addToDataToPrint(SELECT_INTERNATIONAL_CHARS);

        addToDataToPrint(SELECT_LOGO_6);
        addToDataToPrint(PRINT_SELECTED_LOGO_IN_NORMAL_MODE);
        addToDataToPrint(LEFT_JUSTIFICATION);
        addToDataToPrint(CENTER_JUSTIFICATION);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);

        addToDataToPrint(getPrintableByString(PROGRAM_IDENTIFIER));

        addToDataToPrint(LINE_FEED);
        addToDataToPrint(ENLARGE_FONTS);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);
        StringBuilder betslipCompleteCode = new StringBuilder();
        betslipCompleteCode.append(COMPANY_NUM).append("-").append(SHOP_NUM).append("-").append(generateProgressiveMArketsId());
        addToDataToPrint(getPrintableByString(betslipCompleteCode.toString()));

        addToDataToPrint(LINE_FEED);
        addToDataToPrint(RESET_FONTS_DIM);
        addToDataToPrint(LEFT_JUSTIFICATION);
        addToDataToPrint(SELECT_LOGO_BOLD_LINE);
        addToDataToPrint(PRINT_SELECTED_LOGO_IN_NORMAL_MODE);
        addToDataToPrint(FEED_6_DOT_ROWS);

        // loop starts here
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);

        addToDataToPrint(getPrintableByString("9    |14.06.12 18:00                5,00 "));

        addToDataToPrint(EURO_INT);
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);
        addToDataToPrint(getPrintableByString("Italien        Kroatien       "));
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(SELECT_LOGO_THIN_LINE);
        addToDataToPrint(PRINT_SELECTED_LOGO_IN_NORMAL_MODE);
        addToDataToPrint(FEED_3_DOT_ROWS);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);
        addToDataToPrint(getPrintableByString("|-1--|-0--|-2--|"));
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(SELECT_LOGO_THIN_LINE);
        addToDataToPrint(PRINT_SELECTED_LOGO_IN_NORMAL_MODE);
        addToDataToPrint(FEED_3_DOT_ROWS);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);
        addToDataToPrint(getPrintableByString("      |1|--------|X|    3,15|2|--------|"));
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(SELECT_LOGO_THIN_LINE);
        addToDataToPrint(PRINT_SELECTED_LOGO_IN_NORMAL_MODE);
        addToDataToPrint(FEED_6_DOT_ROWS);
        addToDataToPrint(LINE_FEED);
        // loop end here


        addToDataToPrint(RIGHT_JUSTIFICATION);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);
        addToDataToPrint(getPrintableByString("Total******5,00 "));
        addToDataToPrint(EURO_INT);
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(RIGHT_JUSTIFICATION);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);
        addToDataToPrint(getPrintableByString("max. payout      :     15,75 "));
        addToDataToPrint(EURO_INT);
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(LEFT_JUSTIFICATION);
        addToDataToPrint(SET_RELATIVE_PRINTER_POS);
        addToDataToPrint(getPrintableByString("registered 14.06.12 at 12:25:21 h"));
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(SELECT_LOGO_DISCLAIMER);
        addToDataToPrint(PRINT_SELECTED_LOGO_IN_NORMAL_MODE);
        addToDataToPrint(CENTER_JUSTIFICATION);
        addToDataToPrint(BARCODE_UNKNOWN_COMMAND);
        addToDataToPrint(BARCODE_HEIGHT_TO_80);
        addToDataToPrint(SET_PRINTER_AREA_WIDTH);
        addToDataToPrint(SET_BARCODE_WIDTH_TO_3);
        addToDataToPrint(BARCODE_PREAMBLE);
        addToDataToPrint(getPrintableByString("200031000034"));
        addToDataToPrint(END_BARCODE_VALUE);
        addToDataToPrint(LINE_FEED);
        addToDataToPrint(END_BARCODE_VALUE);
        addToDataToPrint(RESET_FONTS_DIM);
        addToDataToPrint(LEFT_JUSTIFICATION);
        addToDataToPrint(SELECT_LOGO_COMPANY);
        addToDataToPrint(PRINT_SELECTED_LOGO_IN_NORMAL_MODE);
        addToDataToPrint(PRINT_AND_CUT);

        for (int i : dataToPrint) {
                           printerData.put(i);
                       }
    }

    private static void addToDataToPrint(Integer[] array) {
        dataToPrint.addAll(Arrays.asList(array));

    }

    private static void addToDataToPrint(ArrayList<Integer> array) {
           dataToPrint.addAll(array);
       }
    
    private static void addToDataToPrint(int value) {
               dataToPrint.add(value);
           }
    




    private static ArrayList<Integer> getPrintableByString(String toPrint) {

           ArrayList<Integer> ret = new ArrayList<>();
           for (char c : toPrint.toCharArray()) {
               ret.add((int) c);
           }
           return ret;

       }

    private static String generateProgressiveMArketsId() {
        return String.format("%06d", betslipCounter++);
    }

}
