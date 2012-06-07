package com.nexse.serial.server.bean;

import java.util.HashMap;

public class MarkSenseCard {





    public static final String RAW_VALUE_SPORTWETTE_CARD = "013E30";
    public static final String RAW_VALUE_SCHEDINA2_CARD = "013630";

    public static final int NUM_RIGHE_CARD_SPORTWETTEN = 54;
    public static final int NUM_RIGHE_CARD_SCHEDINA2 = 54;

    public static final int ONE_ROW_CHARS_NUMBER = 6;


    private String typeStr;
    private String rawString;
    private int row;
    private HashMap<Integer,CardRow> listaRighe = new HashMap<>();

    public MarkSenseCard(String rawDataFromScanner) {
        this.rawString = rawDataFromScanner;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MarkSenseCard");
        sb.append("{typeStr='").append(typeStr).append('\'');
        sb.append(", rawString='").append(rawString).append('\'');
        sb.append(", row=").append(row);
        sb.append(", listaRighe=").append(listaRighe);
        sb.append('}');
        return sb.toString();
    }


}

