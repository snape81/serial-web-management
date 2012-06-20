package com.nexse.serial.server.bean;

import java.util.HashMap;

public class MarkSenseCard {





    public static final String RAW_VALUE_SPORTWETTE_CARD = "013E30";
    public static final String RAW_VALUE_SCHEDINA2_CARD = "013630";
    public static final String RAW_VALUE_SCHEDINA3_CARD = "011D00";
    public static final String RAW_VALUE_SCHEDINA4_CARD = "010230";

    public static final int NUM_RIGHE_CARD_SPORTWETTEN = 54;
    public static final int NUM_RIGHE_CARD_SCHEDINA2 = 54;

    public static final int ONE_ROW_CHARS_NUMBER = 6;


    private String typeStr;
    private String rawString;
    private int row;
    private HashMap<Integer,CardRow> listaRighe = new HashMap<>();
    private boolean valid;

    public MarkSenseCard(String rawDataFromScanner) {



        this.rawString = rawDataFromScanner;
        this.valid = checkValidity(rawDataFromScanner);

        if(!valid)  {
            return;
        }


        int stringLength = rawDataFromScanner.length();


        this.row = stringLength / MarkSenseCard.ONE_ROW_CHARS_NUMBER;
        int actualRow = 0;
        while (actualRow < row) {
            int startIndex = actualRow * ONE_ROW_CHARS_NUMBER;
            CardRow cr ;
            if (actualRow == (row -1)) {
                 cr =new CardRow(rawString.substring(startIndex));
            } else {
                cr = new CardRow(rawString.substring(startIndex,startIndex+ONE_ROW_CHARS_NUMBER));
            }
            listaRighe.put(cr.getRowIndex(),cr);
            actualRow++;
        }
        this.typeStr = listaRighe.get(1).getRawString();
    }

    private boolean checkValidity(String rawDataFromScanner) {
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

    public int getRow() {
        return row;
    }

    public String getRawString() {
        return rawString;
    }

    public boolean isEmpty() {
        return row < 2;
    }

    public HashMap<Integer, CardRow> getListaRighe() {
        return listaRighe;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MarkSenseCard");
        sb.append("{typeStr='").append(typeStr).append('\'');
        sb.append(", rawString='").append(rawString).append('\'');
        sb.append(", row=").append(row);
        sb.append(", listaRighe=").append(listaRighe);
        sb.append(", valid=").append(valid);
        sb.append('}');
        return sb.toString();
    }


}

