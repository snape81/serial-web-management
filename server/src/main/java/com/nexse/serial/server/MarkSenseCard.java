package com.nexse.serial.server;

public class MarkSenseCard {

    private String rawString;

    public String getRawString() {
        return rawString;
    }

    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MarkSenseCard");
        sb.append("{rawString='").append(rawString).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
