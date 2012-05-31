package com.nexse.serial.server;

import com.nexse.serial.server.exchange.EventStringExchange;

public class MainTestExchange {


    public static class SerialWriter implements Runnable {
        private EventStringExchange ex;
        public SerialWriter(EventStringExchange exc) {
            this.ex = exc;
        }

        @Override
        public void run() {
            System.out.println(" WRITER -- aspetto un tempo variabile fino a 10 sec e producio una stringa  ... ");

            int i = 1;
            while (true) {
                System.out.println(" WRITER start iteration ");

               int awaitTime = new Double(10000 * Math.random()).intValue();
                System.out.println("WRITER aspetto " + awaitTime + " millisec");


                try {
                    Thread.sleep(awaitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                System.out.println("WRITER Genero " + "XXXGENERATED" + i);
                ex.put("XXXGENERATED" + i++);
                System.out.println(" WRITER end iteration ");



            }

        }


    }

    public static class SerialReader implements Runnable {
            private EventStringExchange ex;
            public SerialReader(EventStringExchange exc) {
                this.ex = exc;
            }

            @Override
            public void run() {
                System.out.println(" READER -- leggo dall'exchange   ... ");

                int i = 1;
                while (true) {
                    System.out.println(" READER start iteration ");


                    System.out.println("READER controllo il buffer se c'e qualcosa");
                    String mess = ex.get();
                    System.out.println(" READER letto " + mess);



                }

            }


        }

    public static void main(String[] args) {
        EventStringExchange sre = new EventStringExchange();

        (new Thread(new SerialReader(sre))).start();
        (new Thread(new SerialWriter(sre))).start();


    }
}
