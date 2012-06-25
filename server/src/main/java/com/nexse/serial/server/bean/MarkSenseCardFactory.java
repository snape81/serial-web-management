package com.nexse.serial.server.bean;

public class MarkSenseCardFactory {
    public static final String  RAW_VALUE_SPORTWETTE_CARD = "013E30";
     public static final String RAW_VALUE_SCHEDINA2_CARD = "013630";
     public static final String RAW_VALUE_SCHEDINA3_CARD = "011D00";
     public static final String RAW_VALUE_SCHEDINA4_CARD = "010230";


     public static final int ONE_ROW_CHARS_NUMBER = 6;

    public static MarkSenseCard getMscFromString(String rawDataFromScanner ) {
        if (!checkValidity(rawDataFromScanner)) {
          return new MarkSenseCard(rawDataFromScanner,Boolean.FALSE,MarkSenseCard.TYPE_UNKNOWNTYPE);
        }
        if (rawDataFromScanner.startsWith(RAW_VALUE_SPORTWETTE_CARD)) {
            return new SportwettenMarkSenseCard(rawDataFromScanner,Boolean.TRUE);
        } else {
            return new MarkSenseCard(rawDataFromScanner,Boolean.TRUE,MarkSenseCard.TYPE_UNKNOWNTYPE);
        }



    }


    private static boolean checkValidity(String rawDataFromScanner) {
                 // non null o vuota
           if (rawDataFromScanner == null || rawDataFromScanner.equals(" ")) {
               return Boolean.FALSE;
           }
            // lunghezza deve essere > 0 e multiplo di 6
           if (rawDataFromScanner.isEmpty()) {
               return Boolean.FALSE;
           }
           if (rawDataFromScanner.length() % ONE_ROW_CHARS_NUMBER != 0 ) {
               return Boolean.FALSE;
           }
           if(!rawDataFromScanner.startsWith("01")) {
              return Boolean.FALSE;
           }
          return Boolean.TRUE;

       }
}
