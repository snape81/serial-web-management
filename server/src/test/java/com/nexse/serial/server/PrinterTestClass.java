package com.nexse.serial.server;

import com.nexse.serial.server.exchange.EventIntExchange;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PrinterTestClass {
    private static final int LINE_FEED = 10;
    private static final int LOGO_NEXSE_ID = 4;

    public static final Integer[] SCHEDINA_LUNGA_ORIG = new Integer[]{29, 33, 33, 10, 32, 49, 32, 65, 117, 116, 101, 117, 105, 108, 10, 32, 50, 32, 78, 101, 119, 98, 117, 114, 121, 10, 32, 51, 32, 78, 111, 116, 116, 105, 110, 103, 104, 97, 109, 10, 32, 52, 32, 89, 97, 114, 109, 111, 117, 116, 104, 10, 32, 53, 32, 86, 97, 97, 108, 10, 32, 54, 32, 72, 97, 121, 100, 111, 99, 107, 10, 32, 55, 32, 76, 101, 32, 77, 111, 110, 116, 32, 83, 97, 105, 110, 116, 10, 32, 56, 32, 72, 101, 114, 101, 102, 111, 114, 100, 10, 32, 57, 32, 86, 105, 99, 104, 121, 10, 49, 48, 32, 85, 116, 116, 111, 120, 101, 116, 101, 114, 10, 49, 49, 32, 76, 101, 111, 112, 97, 114, 100, 115, 116, 111, 119, 110, 10, 49, 50, 32, 83, 85, 78, 68, 69, 82, 76, 65, 78, 68, 10, 49, 51, 32, 83, 72, 69, 70, 70, 73, 69, 76, 68, 10, 49, 52, 32, 82, 79, 77, 70, 79, 82, 68, 10, 49, 53, 32, 67, 82, 65, 89, 70, 79, 82, 68, 10, 49, 54, 32, 83, 73, 84, 84, 73, 78, 71, 66, 79, 85, 82, 78, 69, 10, 49, 55, 32, 77, 79, 78, 77, 79, 82, 69, 10, 29, 33, 0, 29, 35, 4, 29, 47, 0, 25};
    public static final Integer[] SCHEDINA_LUNGA = new Integer[]{ 27,83,29, 33, 33, 10, 32, 49, 32, 65, 117, 116, 101, 117, 105, 108, 10, 32, 50, 32, 78, 101, 119, 98, 117, 114, 121, 10, 32, 51, 32, 78, 111, 116, 116, 105, 110, 103, 104, 97, 109, 10, 32, 52, 32, 89, 97, 114, 109, 111, 117, 116, 104, 10, 32, 53, 32, 86, 97, 97, 108, 10, 32, 54, 32, 72, 97, 121, 100, 111, 99, 107, 10, 32, 55, 32, 76, 101, 32, 77, 111, 110, 116, 32, 83, 97, 105, 110, 116, 10, 32, 56, 32, 72, 101, 114, 101, 102, 111, 114, 100, 10, 32, 57, 32, 86, 105, 99, 104, 121, 10, 49, 48, 32, 85, 116, 116, 111, 120, 101, 116, 101, 114, 10, 49, 49, 32, 76, 101, 111, 112, 97, 114, 100, 115, 116, 111, 119, 110, 10, 49, 50, 32, 83, 85, 78, 68, 69, 82, 76, 65, 78, 68, 10, 49, 51, 32, 83, 72, 69, 70, 70, 73, 69, 76, 68, 10, 49, 52, 32, 82, 79, 77, 70, 79, 82, 68, 10, 49, 53, 32, 67, 82, 65, 89, 70, 79, 82, 68, 10, 49, 54, 32, 83, 73, 84, 84, 73, 78, 71, 66, 79, 85, 82, 78, 69, 10, 49, 55, 32, 77, 79, 78, 77, 79, 82, 69, 10, 29, 33, 0, 25};

    private static final ArrayList<Integer> CENTER_JUSTIFICATION = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 97, 1}));
    private static final ArrayList<Integer> LEFT_JUSTIFICATION = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 97, 0}));

    private static final ArrayList<Integer> LOGO_PREFIX = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 35}));
    private static final ArrayList<Integer> LOGO_SUFFIX = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 47, 0}));

    private static final ArrayList<Integer> PRINT_AND_CUT = new ArrayList<Integer>(Arrays.asList(new Integer[]{19, 25}));
    private static final ArrayList<Integer> SET_RELATIVE_POSITION = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 92, 65, 0}));
    private static final ArrayList<Integer> RESET_PRINTER = new ArrayList<Integer>(Arrays.asList(new Integer[]{27, 64, 27, 82, 6}));


    //private static final ArrayList<Integer>  = new ArrayList<Integer>(Arrays.asList(new Integer[] {} ));
    private static final ArrayList<Integer> BETSLIPID_CHAR_SIZE_COMMAND = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 33, 33}));
    private static final ArrayList<Integer> RESET_CHAR_SIZE = new ArrayList<Integer>(Arrays.asList(new Integer[]{29, 33, 0}));


    private static int[] testIdChar = new int[]{29, 35, 4, 29, 47, 0, 10, 29, 33, 33, 27, 92, 65, 00, 48, 51, 45, 48, 51, 49, 45, 48, 48, 48, 48, 51, 48, 10, 19, 25};


    public static void main(String[] args) throws Exception {
        EventIntExchange printerData = new EventIntExchange();

        PrinterManager printerManager = new PrinterManager("/dev/ttyUSB0", 115200, 8, 1, 0, printerData);
        printerManager.startConnectionToPrinter(printerData);

        ArrayList<Integer> dataToPrint = new ArrayList<>();
        //addDataToPrintFromSniffed("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/sniffed/test_print.log.OVERVIEW1",dataToPrint);


        dataToPrint.addAll(CENTER_JUSTIFICATION);
        dataToPrint.addAll(getLogo(LOGO_NEXSE_ID));
        dataToPrint.addAll(BETSLIPID_CHAR_SIZE_COMMAND);
        dataToPrint.addAll(getPrintableByString("03-031-000030"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(RESET_CHAR_SIZE);
        dataToPrint.addAll(LEFT_JUSTIFICATION);
        dataToPrint.addAll(CENTER_JUSTIFICATION);
        dataToPrint.addAll(getLogo(LOGO_NEXSE_ID));
        dataToPrint.addAll(LEFT_JUSTIFICATION);
        dataToPrint.addAll(PRINT_AND_CUT);

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

        for (int i : dataToPrint) {
                            printerData.put(i);
                        }
    }

    private static void convertSniffed() throws IOException {
        PrintWriter writer =
                new PrintWriter(
                        new BufferedWriter(
                                new FileWriter("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/sniffed/test_print.log.OVERVIEW1.HEX", false)));

        String s;
        BufferedReader reader =
                new BufferedReader(
                        new FileReader("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/sniffed/test_print.log.OVERVIEW1"));

        while ((s = reader.readLine()) != null) {//System.out.println(s);

            System.out.println(s.replaceAll("^.*\\(", "").replaceAll("\\)", ""));

            writer.println(s + " HEX---> (" + Integer.toHexString(Integer.parseInt(s.replaceAll("^.*\\(", "").replaceAll("\\)", ""))) + ")");


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

