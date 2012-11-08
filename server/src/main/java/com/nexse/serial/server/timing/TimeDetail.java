package com.nexse.serial.server.timing;

public class TimeDetail implements Comparable{

    public TimeDetail(String operation, long nanos, long delta) {
        this.operation = operation;
        this.nanos = nanos;
        this.delta = delta;
    }

    public String operation;
    public long nanos;
    public long delta;


    @Override
    public int compareTo(Object o) {
        TimeDetail other = (TimeDetail) o;
        return Long.compare(this.nanos,other.nanos);
    }
}
