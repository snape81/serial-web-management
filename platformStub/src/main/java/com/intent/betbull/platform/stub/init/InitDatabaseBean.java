package com.intent.betbull.platform.stub.init;

import com.intent.betbull.platform.stub.entity.MarketDetail;

public class InitDatabaseBean {


    public void init() {
        MarketDetail m = new MarketDetail();
        m.setCode("12AB");
        m.setTeam1("Roma"); m.setTeam2("Lazio");
        m.setOdd_result_0(1.05F);
        m.setOdd_result_1(2.25F);
        m.setOdd_result_2(4.85F);
        m.persist();

        MarketDetail m1 = new MarketDetail();
                m1.setCode("34BF");
                m1.setTeam1("Milan"); m1.setTeam2("Inter");
                m1.setOdd_result_0(1.35F);
                m1.setOdd_result_1(1.80F);
                m1.setOdd_result_2(2.22F);
                m1.persist();

        MarketDetail m2 = new MarketDetail();
                        m2.setCode("7CF");
                        m2.setTeam1("Juventus"); m2.setTeam2("Chievo Verona");
                        m2.setOdd_result_0(1.01F);
                        m2.setOdd_result_1(3.80F);
                        m2.setOdd_result_2(11.20F);
                        m2.persist();

        MarketDetail m3 = new MarketDetail();
                        m3.setCode("4ADE");
                        m3.setTeam1("Lecce"); m3.setTeam2("bari");
                        m3.setOdd_result_0(4.05F);
                        m3.setOdd_result_1(2.90F);
                        m3.setOdd_result_2(1.85F);
                        m3.persist();

        MarketDetail m4 = new MarketDetail();
                        m4.setCode("98EF");
                        m4.setTeam1("Catania"); m4.setTeam2("Palermo");
                        m4.setOdd_result_0(7.55F);
                        m4.setOdd_result_1(2.30F);
                        m4.setOdd_result_2(2.05F);
                        m4.persist();

        MarketDetail m5 = new MarketDetail();
                        m5.setCode("2C");
                        m5.setTeam1("Udinese"); m5.setTeam2("Bologna");
                        m5.setOdd_result_0(1.30F);
                        m5.setOdd_result_1(3.10F);
                        m5.setOdd_result_2(6.50F);
                        m5.persist();




    }


}
