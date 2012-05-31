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
        System.out.println("Producing " + readed + " ...");
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
        System.out.println("Consuming " +  ret  + " ...");
        notify();
        return ret;
    }

}
