package com.nexse.serial.server.exchange;

import com.nexse.serial.server.MarkSenseCard;

public class EventMarkSenseExchange {
    private MarkSenseCard buffer;

    public synchronized void put(MarkSenseCard readed) {
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

    public synchronized MarkSenseCard get() {

        if (buffer == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            } finally {
            }
        }

        MarkSenseCard ret = buffer;
        buffer = null;
        notify();
        return ret;
    }

}
