package com.nexse.serial.server;

public class MarkSenseCard {
    public static final int ONE_ROW_CHARS_NUMBER = 6;

    private String rawString;

    private int row;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getRawString() {
        return rawString;
    }

    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    public boolean isEmpty() {
        return row < 2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MarkSenseCard");
        sb.append("{rawString='").append(rawString).append('\'');
        sb.append(", row=").append(row);
        sb.append('}');
        return sb.toString();
    }
}
