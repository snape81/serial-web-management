package com.nexse.serial.server.exchange;

public class EventIntExchange {
    private Integer buffer;

        public synchronized void put(Integer readed) {
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

        public synchronized Integer get() {

            if (buffer == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                } finally {
                }
            }

            Integer ret = buffer;
            buffer = null;
            System.out.println("Consuming " +  ret  + " ...");
            notify();
            return ret;
        }

}
