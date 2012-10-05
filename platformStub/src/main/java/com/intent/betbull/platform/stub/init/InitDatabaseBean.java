package com.intent.betbull.platform.stub.init;

import com.intent.betbull.platform.stub.entity.MarketDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InitDatabaseBean {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    public void init() throws ParseException {
        MarketDetail m = new MarketDetail();
        m.setCode("12AB");
        m.setTeam1("Augsburg"); m.setTeam2("Bremen");
        m.setEventDate(sdf.parse("25/10/2012 20:45:00"));
        m.setOdd_result_0(1.05F);
        m.setOdd_result_1(2.25F);
        m.setOdd_result_2(4.85F);
        m.persist();

        MarketDetail m1 = new MarketDetail();
                m1.setCode("34BF");
                m1.setTeam1("Stuttgart"); m1.setTeam2("Leverkusen");
        m1.setEventDate(sdf.parse("25/10/2012 20:45:00"));
                m1.setOdd_result_0(1.35F);
                m1.setOdd_result_1(1.80F);
                m1.setOdd_result_2(2.22F);
                m1.persist();

        MarketDetail m2 = new MarketDetail();
                        m2.setCode("7CF");
                        m2.setTeam1("Hannover 96"); m2.setTeam2("Dortmund");
        m2.setEventDate(sdf.parse("26/10/2012 18:00:00"));
                        m2.setOdd_result_0(1.01F);
                        m2.setOdd_result_1(3.80F);
                        m2.setOdd_result_2(11.20F);
                        m2.persist();

        MarketDetail m3 = new MarketDetail();
                        m3.setCode("4ADE");
                        m3.setTeam1("M'gladbach"); m3.setTeam2("Frankfurt");
        m3.setEventDate(sdf.parse("26/10/2012 18:00:00"));
                        m3.setOdd_result_0(4.05F);
                        m3.setOdd_result_1(2.90F);
                        m3.setOdd_result_2(1.85F);
                        m3.persist();

        MarketDetail m4 = new MarketDetail();
                        m4.setCode("98EF");
                        m4.setTeam1("Sandhausen"); m4.setTeam2("VfR Aalen");
        m4.setEventDate(sdf.parse("26/10/2012 12:00:00"));
                        m4.setOdd_result_0(7.55F);
                        m4.setOdd_result_1(2.30F);
                        m4.setOdd_result_2(2.05F);
                        m4.persist();

        MarketDetail m5 = new MarketDetail();
                        m5.setCode("2C");
                        m5.setTeam1("ngolstadt"); m5.setTeam2("1.FC K`lautern");
        m5.setEventDate(sdf.parse("26/10/2012 20:45:00"));
                        m5.setOdd_result_0(1.30F);
                        m5.setOdd_result_1(3.10F);
                        m5.setOdd_result_2(6.50F);
                        m5.persist();




    }


}
