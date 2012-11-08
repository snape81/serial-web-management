package com.nexse.serial.server.bean;

import com.nexse.serial.server.timing.TimingArchive;

import java.util.HashMap;

public class MarkSenseCard {

    public static final int TYPE_UNKNOWNTYPE = 0;
    public static final int TYPE_SPORTWETTEN = 1;


    private String typeStr;
    private String rawString;
    private int row;
    private HashMap<Integer,CardRow> listaRighe = new HashMap<>();
    private boolean valid;
    private int typeId;

    public MarkSenseCard(String rawDataFromScanner,Boolean validread,int typeID) {
        TimingArchive.getCurrent().setPreSuperclassSportwetteInstantiation();


        this.typeId = typeID;
        this.rawString = rawDataFromScanner;
        this.valid = validread;

        if(!valid)  {
            return;
        }


        int stringLength = rawDataFromScanner.length();


        this.row = stringLength / MarkSenseCardFactory.ONE_ROW_CHARS_NUMBER;
        int actualRow = 0;
        while (actualRow < row) {
            int startIndex = actualRow * MarkSenseCardFactory.ONE_ROW_CHARS_NUMBER;
            CardRow cr ;
            if (actualRow == (row -1)) {
                 cr =new CardRow(rawString.substring(startIndex));
            } else {
                cr = new CardRow(rawString.substring(startIndex,startIndex+MarkSenseCardFactory.ONE_ROW_CHARS_NUMBER));
            }
            listaRighe.put(cr.getRowIndex(),cr);
            actualRow++;
        }
        this.typeId = TYPE_UNKNOWNTYPE;
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

    public boolean isValid() {
        return valid;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
        sb.append(", typeId=").append(typeId);
        sb.append('}');
        return sb.toString();
    }


}

