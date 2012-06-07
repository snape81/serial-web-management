package com.nexse.serial.server.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CardRow {
    static final Logger logger = LoggerFactory.getLogger(CardRow.class);

    public static final int BITMASK_MARK_1 = 0x2000;
        public static final int BITMASK_MARK_2 = 0x1000;
        public static final int BITMASK_MARK_3 = 0x800;
        public static final int BITMASK_MARK_4 = 0x400;
        public static final int BITMASK_MARK_5 = 0x200;
        public static final int BITMASK_MARK_6 = 0x100;
        public static final int BITMASK_MARK_7 = 0x80;
        public static final int BITMASK_MARK_8 = 0x40;
        public static final int BITMASK_MARK_9 = 0x20;
        public static final int BITMASK_MARK_10 = 0x10;
        public static final int BITMASK_MARK_11 = 0x8;
        public static final int BITMASK_MARK_12 = 0x4;
        public static final int BITMASK_MARK_13 = 0x2;
        public static final int BITMASK_MARK_14 = 0x1;


    public static final int START_INDEX_ROW_NUM = 0;
    public static final int END_INDEX_ROW_NUM = 2; // substring estremo destro escluso
    public static final int START_INDEX_DATA = 2;
    public static final int NUMBER_OF_CELLS_PER_ROW = 14;

    private String rawString;
    private int rowIndex;
    private boolean[] markedCells;

    public String getRawString() {
        return rawString;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public boolean[] getMarkedCells() {
        return markedCells;
    }

    public CardRow(String rawString) {
        logger.debug(" Instantiating card row from rwastring {}" ,rawString);
        this.rawString = rawString;
        this.rowIndex = Integer.parseInt(rawString.substring(START_INDEX_ROW_NUM,END_INDEX_ROW_NUM));
        int wholeRow = Integer.parseInt(rawString.substring(START_INDEX_DATA),16);
        markedCells = new boolean[NUMBER_OF_CELLS_PER_ROW];
        markedCells[0]  =   (wholeRow & BITMASK_MARK_1)  == BITMASK_MARK_1;
        markedCells[1]  =   (wholeRow  & BITMASK_MARK_2)  == BITMASK_MARK_2;
        markedCells[2]  =   (wholeRow  & BITMASK_MARK_3)  == BITMASK_MARK_3;
        markedCells[3]  =   (wholeRow  & BITMASK_MARK_4)  == BITMASK_MARK_4;
        markedCells[4]  =   (wholeRow  & BITMASK_MARK_5)  == BITMASK_MARK_5;
        markedCells[5]  =   (wholeRow  & BITMASK_MARK_6)  == BITMASK_MARK_6;
        markedCells[6]  =   (wholeRow  & BITMASK_MARK_7)  == BITMASK_MARK_7;
        markedCells[7]  =   (wholeRow  & BITMASK_MARK_8)  == BITMASK_MARK_8;
        markedCells[8]  =   (wholeRow  & BITMASK_MARK_9)  == BITMASK_MARK_9;
        markedCells[9]  =   (wholeRow  & BITMASK_MARK_10) == BITMASK_MARK_10;
        markedCells[10] =   (wholeRow  & BITMASK_MARK_11) == BITMASK_MARK_11;
        markedCells[11] =   (wholeRow  & BITMASK_MARK_12) == BITMASK_MARK_12;
        markedCells[12] =   (wholeRow  & BITMASK_MARK_13) == BITMASK_MARK_13;
        markedCells[13] =   (wholeRow  & BITMASK_MARK_14) == BITMASK_MARK_14;

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CardRow");
        sb.append("{rawString='").append(rawString).append('\'');
        sb.append(", rowIndex=").append(rowIndex);
        sb.append(", markedCells=").append(markedCells == null ? "null" : Arrays.toString(markedCells));
        sb.append('}');
        return sb.toString();
    }
}
