package com.nexse.serial.server.exchange;

import java.util.ArrayList;

public class EventIntArraylistExchange {
    private ArrayList<Integer> buffer;

    public synchronized void put(ArrayList<Integer> readed) {
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

    public synchronized ArrayList<Integer> get() {

        if (buffer == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            } finally {
            }
        }

        ArrayList<Integer> ret = buffer;
        buffer = null;
        notify();
        return ret;
    }

}
