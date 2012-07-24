package com.nexse.serial.server;

import com.nexse.serial.server.exchange.EventIntExchange;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PrinterTestClass {
    private static final int LINE_FEED = 10;
    private static final int LOGO_NEXSE_ID = 4;


    public static final Integer[] BETSLIP2 = new Integer[]{27,64,27,82,6,29,35,6,29,47,0,27,97,48,27,97,49,27,92,65,0,32,32,50,52,47,50,48,49,50,10,29,33,33,27,92,65,0,48,51,45,48,51,49,45,48,48,48,48,51,52,10,29,33,0,27,97,48,29,35,12,29,47,0,21,6,27,92,65,0,57,32,32,32,32,124,49,52,46,48,54,46,49,50,32,49,56,58,48,48,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,53,44,48,48,32,213,10,27,92,65,0,73,116,97,108,105,101,110,32,32,32,32,32,32,32,32,75,114,111,97,116,105,101,110,32,32,32,32,32,32,32,10,29,35,11,29,47,0,21,3,27,92,65,0,124,45,49,45,45,124,45,48,45,45,124,45,50,45,45,124,10,29,35,11,29,47,0,21,3,27,92,65,0,32,32,32,32,32,32,124,49,124,45,45,45,45,45,45,45,45,124,88,124,32,32,32,32,51,44,49,53,124,50,124,45,45,45,45,45,45,45,45,124,10,29,35,11,29,47,0,21,6,27,97,50,27,92,65,0,84,111,116,97,108,42,42,42,42,42,42,53,44,48,48,32,213,10,27,97,48,27,97,50,27,92,65,0,109,97,120,46,32,112,97,121,111,117,116,32,32,32,32,32,32,58,32,32,32,32,32,49,53,44,55,53,32,213,10,27,97,48,27,92,65,0,114,101,103,105,115,116,101,114,101,100,32,49,52,46,48,54,46,49,50,32,97,116,32,49,50,58,50,53,58,50,49,32,104,10,29,35,20,29,47,0,27,97,49,29,79,0,29,104,80,29,87,1,3,29,119,3,29,107,2,50,48,48,48,51,49,48,48,48,48,51,52,0,10,0,29,33,0,27,97,48,29,35,5,29,47,0,25 };
    public static final Integer[] SCHEDINA_LUNGA_ORIG = new Integer[]{29, 33, 33, 10, 32, 49, 32, 65, 117, 116, 101, 117, 105, 108, 10, 32, 50, 32, 78, 101, 119, 98, 117, 114, 121, 10, 32, 51, 32, 78, 111, 116, 116, 105, 110, 103, 104, 97, 109, 10, 32, 52, 32, 89, 97, 114, 109, 111, 117, 116, 104, 10, 32, 53, 32, 86, 97, 97, 108, 10, 32, 54, 32, 72, 97, 121, 100, 111, 99, 107, 10, 32, 55, 32, 76, 101, 32, 77, 111, 110, 116, 32, 83, 97, 105, 110, 116, 10, 32, 56, 32, 72, 101, 114, 101, 102, 111, 114, 100, 10, 32, 57, 32, 86, 105, 99, 104, 121, 10, 49, 48, 32, 85, 116, 116, 111, 120, 101, 116, 101, 114, 10, 49, 49, 32, 76, 101, 111, 112, 97, 114, 100, 115, 116, 111, 119, 110, 10, 49, 50, 32, 83, 85, 78, 68, 69, 82, 76, 65, 78, 68, 10, 49, 51, 32, 83, 72, 69, 70, 70, 73, 69, 76, 68, 10, 49, 52, 32, 82, 79, 77, 70, 79, 82, 68, 10, 49, 53, 32, 67, 82, 65, 89, 70, 79, 82, 68, 10, 49, 54, 32, 83, 73, 84, 84, 73, 78, 71, 66, 79, 85, 82, 78, 69, 10, 49, 55, 32, 77, 79, 78, 77, 79, 82, 69, 10, 29, 33, 0, 29, 35, 4, 29, 47, 0, 25};
    public static final Integer[] SCHEDINA_LUNGA = new Integer[]{ 27,83,29, 33, 33, 10, 32, 49, 32, 65, 117, 116, 101, 117, 105, 108, 10, 32, 50, 32, 78, 101, 119, 98, 117, 114, 121, 10, 32, 51, 32, 78, 111, 116, 116, 105, 110, 103, 104, 97, 109, 10, 32, 52, 32, 89, 97, 114, 109, 111, 117, 116, 104, 10, 32, 53, 32, 86, 97, 97, 108, 10, 32, 54, 32, 72, 97, 121, 100, 111, 99, 107, 10, 32, 55, 32, 76, 101, 32, 77, 111, 110, 116, 32, 83, 97, 105, 110, 116, 10, 32, 56, 32, 72, 101, 114, 101, 102, 111, 114, 100, 10, 32, 57, 32, 86, 105, 99, 104, 121, 10, 49, 48, 32, 85, 116, 116, 111, 120, 101, 116, 101, 114, 10, 49, 49, 32, 76, 101, 111, 112, 97, 114, 100, 115, 116, 111, 119, 110, 10, 49, 50, 32, 83, 85, 78, 68, 69, 82, 76, 65, 78, 68, 10, 49, 51, 32, 83, 72, 69, 70, 70, 73, 69, 76, 68, 10, 49, 52, 32, 82, 79, 77, 70, 79, 82, 68, 10, 49, 53, 32, 67, 82, 65, 89, 70, 79, 82, 68, 10, 49, 54, 32, 83, 73, 84, 84, 73, 78, 71, 66, 79, 85, 82, 78, 69, 10, 49, 55, 32, 77, 79, 78, 77, 79, 82, 69, 10, 29, 33, 0, 25};

    private static final ArrayList<Integer> CENTER_JUSTIFICATION = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 97, 1}));
    private static final ArrayList<Integer> LEFT_JUSTIFICATION = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 97, 0}));

    private static final ArrayList<Integer> INIT_PRINTER_AND_CHARSET = new ArrayList<Integer>(Arrays.asList(new Integer[]{27,64,27,82,6}));

    private static final ArrayList<Integer> LOGO_PREFIX = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 35}));
    private static final ArrayList<Integer> LOGO_SUFFIX = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 47, 0}));

    private static final ArrayList<Integer> BARCODE_HEIGHT = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 104, 80}));
    private static final ArrayList<Integer> BARCODE_PRINTING_AREA_WIDTH = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 87, 1, 3}));
    private static final ArrayList<Integer> BARCODE_WIDTH = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 119, 3}));
    private static final ArrayList<Integer> BARCODE_PREFIX = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 107}));




    private static final ArrayList<Integer> PRINT_AND_CUT = new ArrayList<Integer>(Arrays.asList(new Integer[]{19, 25}));
    private static final ArrayList<Integer> SET_RELATIVE_POSITION = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 92, 65, 0}));
    private static final ArrayList<Integer> RESET_PRINTER = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 64, 27, 82, 6}));


    private static final ArrayList<Integer> BOLD_LINE = new ArrayList<Integer>(Arrays.asList(new Integer[]{29,35,12,29,47,0,21,3,27,92,0,0}));
    private static final ArrayList<Integer> THIN_LINE = new ArrayList<Integer>(Arrays.asList(new Integer[]{29,35,11,29,47,0,21,3}));
    private static final ArrayList<Integer> LEFT_JUSTIFICATION_SPACE = getPrintableByString("     ");



    //private static final ArrayList<Integer>  = new ArrayList<Integer>(Arrays.asList(new Integer[] {} ));
    private static final ArrayList<Integer> BETSLIPID_CHAR_SIZE_COMMAND = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 33, 33}));
    private static final ArrayList<Integer> RESET_CHAR_SIZE = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 33, 0}));


    private static int[] testIdChar = new int[]{29, 35, 4, 29, 47, 0, 10, 29, 33, 33, 27, 92, 65, 00, 48, 51, 45, 48, 51, 49, 45, 48, 48, 48, 48, 51, 48, 10, 19, 25};


    public static void main(String[] args) throws Exception {

        /*EventIntExchange printerData = new EventIntExchange();

        PrinterManager printerManager = new PrinterManager("/dev/ttyUSB0", 115200, 8, 1, 0, printerData);
              printerManager.startConnectionToPrinter(printerData);

        ArrayList<Integer> dataToPrint = new ArrayList<>();
        dataToPrint.addAll(Arrays.asList(BETSLIP2));

        for (int i : dataToPrint) {
                    printerData.put(i);
                }*/

        convertSniffed();
    }

    public static void smain(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        EventIntExchange printerData = new EventIntExchange();

        PrinterManager printerManager = new PrinterManager("/dev/ttyUSB0", 115200, 8, 1, 0, printerData);
        printerManager.startConnectionToPrinter(printerData);

        ArrayList<Integer> dataToPrint = new ArrayList<>();
        //addDataToPrintFromSniffed("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/sniffed/test_print.log.OVERVIEW1",dataToPrint);

        dataToPrint.addAll(INIT_PRINTER_AND_CHARSET);

        dataToPrint.addAll(CENTER_JUSTIFICATION);
            dataToPrint.addAll(getPrintableByString("32/2012"));
            dataToPrint.add(LINE_FEED);
            dataToPrint.addAll(BETSLIPID_CHAR_SIZE_COMMAND);
                dataToPrint.addAll(getPrintableByString("03-031-000030"));
            dataToPrint.addAll(RESET_CHAR_SIZE);
            dataToPrint.add(LINE_FEED);
           dataToPrint.addAll(LEFT_JUSTIFICATION);
   //       dataToPrint.addAll(CENTER_JUSTIFICATION);
    //    dataToPrint.addAll(getPrintableByString("recorded "));
    //      dataToPrint.addAll(LEFT_JUSTIFICATION);
        dataToPrint.addAll(BOLD_LINE);
        dataToPrint.addAll(THIN_LINE);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("|                          STAKE: 3,00 "));dataToPrint.add(213);dataToPrint.addAll(getPrintableByString("   |"));
        dataToPrint.add(LINE_FEED);
                          // repeat here
        dataToPrint.addAll(THIN_LINE);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("| # 1 | 12AB    | 26/08/2012 at 20:45      |"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("| team1 Vs. team2                  |       |"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("|1| - |X| 12,50 |2| - ||         37,50 "));dataToPrint.add(213);dataToPrint.addAll(getPrintableByString("   |"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(THIN_LINE);
        dataToPrint.addAll(THIN_LINE);
               dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
               dataToPrint.addAll(getPrintableByString("| # 1 | 12AB    | 26/08/2012 at 20:45      |"));
               dataToPrint.add(LINE_FEED);
               dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
               dataToPrint.addAll(getPrintableByString("| team1 Vs. team2                  |       |"));
               dataToPrint.add(LINE_FEED);
               dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("|1| - |X| 12,50 |2| - ||         37,50 "));dataToPrint.add(213);dataToPrint.addAll(getPrintableByString("   |"));

               dataToPrint.add(LINE_FEED);
               dataToPrint.addAll(THIN_LINE);
        dataToPrint.addAll(THIN_LINE);
               dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
               dataToPrint.addAll(getPrintableByString("| # 1 | 12AB    | 26/08/2012 at 20:45      |"));
               dataToPrint.add(LINE_FEED);
               dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
               dataToPrint.addAll(getPrintableByString("| team1 Vs. team2                  |       |"));
               dataToPrint.add(LINE_FEED);
               dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("|1| - |X| 12,50 |2| - ||         37,50 "));dataToPrint.add(213);dataToPrint.addAll(getPrintableByString("   |"));
               dataToPrint.add(LINE_FEED);
               dataToPrint.addAll(THIN_LINE);


        dataToPrint.addAll(BOLD_LINE);
        dataToPrint.addAll(THIN_LINE);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("|                      Total cost: 9,00  "));dataToPrint.add(213);dataToPrint.addAll(getPrintableByString(" |"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("|                     Total win: 215,00  "));dataToPrint.add(213);dataToPrint.addAll(getPrintableByString(" |"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(THIN_LINE);
        dataToPrint.addAll(BOLD_LINE);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("registered " + sdf.format(new Date())));
        dataToPrint.add(LINE_FEED);

        dataToPrint.addAll(BARCODE_PRINTING_AREA_WIDTH);
        dataToPrint.addAll(BARCODE_WIDTH);
        dataToPrint.addAll(BARCODE_PREFIX);
        dataToPrint.addAll(getPrintableByString("20120310330"));
        dataToPrint.add(0);
        dataToPrint.add(LINE_FEED);






        /*dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("| # 1 | 12AB    | 26/08/2012 at 20:45      |"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("| team1 Vs. team2                  |       |"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(LEFT_JUSTIFICATION_SPACE);
        dataToPrint.addAll(getPrintableByString("|1| - |X| 12,50 |2| - ||         37,50 eur |"));
        dataToPrint.add(LINE_FEED);

        dataToPrint.addAll(THIN_LINE);*/




      //  dataToPrint.addAll(CENTER_JUSTIFICATION);
       // dataToPrint.addAll(getLogo(LOGO_NEXSE_ID));
       // dataToPrint.addAll(LEFT_JUSTIFICATION);

        //dataToPrint.addAll(PRINT_AND_CUT);
        dataToPrint.add(25);

        /*dataToPrint.addAll(new ArrayList<>(Arrays.asList(new Integer[]{29,33,33})));
        dataToPrint.addAll(SET_RELATIVE_POSITION);
        dataToPrint.addAll(getPrintableByString("03-031-000030"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(RESET_CHAR_SIZE);
        dataToPrint.addAll(LEFT_JUSTIFICATION);
        dataToPrint.addAll(getLogo(LOGO_NEXSE_ID));
        dataToPrint.addAll(feedNDotRows(6));
        dataToPrint.addAll(SET_RELATIVE_POSITION);
        dataToPrint.addAll(getPrintableByString("2356F|12.06.12 12:00               3,00€"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(PRINT_AND_CUT);*/


        //convertSniffed(printerData, dataToPrint);


        for (int i : dataToPrint) {
                    printerData.put(i);
                }

    }

    private static void convertSniffed() throws IOException {
        PrintWriter writer =
                new PrintWriter(
                        new BufferedWriter(
                                new FileWriter("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/sniffed/test_print.log.BETSLIP2b.HEX", false)));

        String s;
        BufferedReader reader =
                new BufferedReader(
                        new FileReader("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/sniffed/test_print.log.BETSLIP2"));

        while ((s = reader.readLine()) != null) {//System.out.println(s);

            System.out.println(s.replaceAll("^.*\\(", "").replaceAll("\\)", ""));

            writer.println(s + " HEX---> (" + Integer.toHexString(Integer.parseInt(s.replaceAll("^.*\\(", "").replaceAll("\\)", ""))) + ") ----> Char " + (char) Integer.parseInt(s.replaceAll("^.*\\(", "").replaceAll("\\)", "")));


        }



        reader.close();
        writer.close();


    }

    private static void addDataToPrintFromSniffed(String sniffedFilepath, ArrayList<Integer> dataToPrint) throws IOException {
        String s;
               BufferedReader reader =
                       new BufferedReader(
                               new FileReader(sniffedFilepath));


        while ((s = reader.readLine()) != null) {//System.out.println(s);

            System.out.println(s.replaceAll("^.*\\(", "").replaceAll("\\)", ""));

            dataToPrint.add(Integer.parseInt(s.replaceAll("^.*\\(", "").replaceAll("\\)", "")));


        }

    }

    private static ArrayList<Integer> getLogo(int numLogo) {
        ArrayList<Integer> ret = new ArrayList<>();
        ret.addAll(LOGO_PREFIX);
        ret.add(numLogo);
        ret.addAll(LOGO_SUFFIX);
        return ret;
    }

    private static ArrayList<Integer> getPrintableByString(String toPrint) {

        ArrayList<Integer> ret = new ArrayList<>();
        for (char c : toPrint.toCharArray()) {
            ret.add((int) c);
        }
        return ret;

    }

    private static ArrayList<Integer> feedNDotRows(int num) {
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(21);
        ret.add(num);
        return ret;
    }


}

