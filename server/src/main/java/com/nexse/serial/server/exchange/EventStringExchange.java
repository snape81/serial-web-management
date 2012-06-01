package com.nexse.serial.server.exchange;

public class EventStringExchange {
    private String buffer;

    public synchronized void put(String readed) {
        if (buffer != null) {
            try {
                wait();
            } catch (InterruptedException e) {
            } finally {
            }
        }
        buffer = readed;
        notify();
    }

    public synchronized String get() {

        if (buffer == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            } finally {
            }
        }

        String ret = buffer;
        buffer = null;
        notify();
        return ret;
    }

}
