package com.nexse.serial.server;

import com.nexse.serial.server.exchange.EventIntExchange;

import java.util.ArrayList;
import java.util.Arrays;

public class PrinterTestClass {
    public static final int LINE_FEED = 10;
    public static final int LOGO_NEXSE_ID = 4;



    private static final ArrayList<Integer> CENTER_JUSTIFICATION = new ArrayList<Integer>(Arrays.asList(new Integer[] {27,97,1}));
    private static final ArrayList<Integer> LEFT_JUSTIFICATION = new ArrayList<Integer>(Arrays.asList(new Integer[] {27,97,0}));

    private static final ArrayList<Integer> LOGO_PREFIX = new ArrayList<Integer>(Arrays.asList(new Integer[] {29,35} ));
    private static final ArrayList<Integer> LOGO_SUFFIX = new ArrayList<Integer>(Arrays.asList(new Integer[] {29,47,0} ));

    private static final ArrayList<Integer> PRINT_AND_CUT = new ArrayList<Integer>(Arrays.asList(new Integer[] {19,25} ));


    //private static final ArrayList<Integer>  = new ArrayList<Integer>(Arrays.asList(new Integer[] {} ));
    private static final ArrayList<Integer> BETSLIPID_CHAR_SIZE_COMMAND = new ArrayList<Integer>(Arrays.asList(new Integer[] {29,33,33} ));


    private static int[] testIdChar = new int[]{29,35,4,29,47,0,10,  29,33,33,27,92,65,00,48,51,45,48,51,49,45,48,48,48,48,51,48,10,19,25};
    
    
    public static void main(String[] args) throws Exception {
        EventIntExchange printerData = new EventIntExchange();
 
        PrinterManager printerManager = new PrinterManager("/dev/ttyUSB0",115200,8,1,0,printerData );
        printerManager.startConnectionToPrinter(printerData);

        ArrayList<Integer> dataToPrint = new ArrayList<>();

        dataToPrint.addAll(CENTER_JUSTIFICATION);
        dataToPrint.addAll(getLogo(LOGO_NEXSE_ID));
        dataToPrint.add(LINE_FEED);
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(BETSLIPID_CHAR_SIZE_COMMAND);
        dataToPrint.addAll(getPrintableByString("03-031-000030"));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(getPrintableByString("cccc "));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(getPrintableByString("cccc "));
        dataToPrint.add(LINE_FEED);
        dataToPrint.addAll(PRINT_AND_CUT);


        /*PrintWriter writer =
        		new PrintWriter(
        			new BufferedWriter(
        				new FileWriter("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/java/testWriter.log", false)));

        String s;
        	BufferedReader reader =
        		new BufferedReader(
        			new FileReader("/home/claudio/work/projects/BETBULL/serial-web-management/server/src/test/java/printerschedina.log") );

        	while( (s = reader.readLine()) != null )
            {//System.out.println(s);

                System.out.println(s.replaceAll("^.*\\(","").replaceAll("\\)",""));

                writer.println(s + " HEX---> ("+Integer.toHexString(Integer.parseInt(s.replaceAll("^.*\\(","").replaceAll("\\)","")))+")");


            }

        	reader.close();
            writer.close();*/

        for (int i : dataToPrint) {
            printerData.put(i);
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

}

