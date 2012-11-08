package com.nexse.serial.server.bean;

import com.nexse.serial.server.detailClient.MarketDetail;
import com.nexse.serial.server.detailClient.MarketDetailsTranslatorClient;
import flexjson.JSONDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SportwettenBettedEvent {
    static final Logger logger = LoggerFactory.getLogger(SportwettenBettedEvent.class);


    public int[] kursTranslator = new int[]{1,0,2};
    public String[] marketCodeTranslator = new String[]{"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public int[] programmTranslator = new int[]{1000,100,10,1};

        public static final int ERGENISWETTE_THICK_NUMBER = 7;
        public static final int SPECIALS_THICK_NUMBER = 7;
        public static final int KURS_THICK_NUMBER = 3;
        public static final int PROGRAMM_THICK_NUMBER = 4;
        public static final int MARKET_CODE_THICK_NUMBER = 15;

        public ArrayValueContainer ergebnisewette_A_Readed;

        public ArrayValueContainer ergebnisewette_B_Readed;

        public ArrayValueContainer kurs_Readed;

        public ArrayValueContainer specials_Readed;

        public ArrayValueContainer market_code_Readed;

        public ArrayValueContainer programm_Readed;

        public String marketCode;

        public boolean combiChecked;

        public boolean bankChecked;

        public boolean ticked;

        public Integer[] programmTick;

        public Integer[]  specials;

        public Integer[]  kurs;

        public String team1;

        public String team2;

        public float odd = 1.00F;

        public boolean presentOnDB;

        public float winningFloat;

        public Date eventDate;


    public SportwettenBettedEvent(int offsetErgebniswetteB, int offsetErgebniswetteA, int offsetKurs, int offsetSpecials, int offsetProgramm,int offset_market_code) {
             ticked = false;
             combiChecked = false;
             bankChecked = false;
             marketCode = "";
             ergebnisewette_A_Readed = new ArrayValueContainer(ERGENISWETTE_THICK_NUMBER,offsetErgebniswetteA);
             ergebnisewette_B_Readed = new ArrayValueContainer(ERGENISWETTE_THICK_NUMBER,offsetErgebniswetteB);
             kurs_Readed = new ArrayValueContainer(KURS_THICK_NUMBER,offsetKurs);
             specials_Readed = new ArrayValueContainer(SPECIALS_THICK_NUMBER,offsetSpecials);
             market_code_Readed = new ArrayValueContainer(MARKET_CODE_THICK_NUMBER,offset_market_code);
             programm_Readed = new ArrayValueContainer(PROGRAMM_THICK_NUMBER,offsetProgramm);
        }

    public void createMarketCode() {

        if (market_code_Readed.getSettledLength() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Integer tickedIndex : market_code_Readed.getAllSettledIdx()) {
                sb.append(marketCodeTranslator[tickedIndex]);
            }

            marketCode = sb.toString();

        }

    }

    public boolean isTicked() {
        return ticked;
    }

    public void setCombiChecked(boolean combiChecked) {
        this.ticked = true;
        this.combiChecked = combiChecked;
    }

    public void setBankChecked(boolean bankChecked) {
        this.ticked = true;
        this.bankChecked = bankChecked;
    }


    public void setErgebnisewette_A(int index) {
        this.ticked = true;
        ergebnisewette_A_Readed.setAReadedValue(index);
    }

    public void setErgebnisewette_B(int index) {
        this.ticked = true;
            ergebnisewette_B_Readed.setAReadedValue(index);
    }

    public void setKurs(int index) {
        this.kurs_Readed.setAReadedValue(index);
        this.ticked = true;
    }

    public void setSpecials(int index) {
        this.specials_Readed.setAReadedValue(index);
        this.ticked = true;
    }

    public void setMarket_code(int index) {
        this.market_code_Readed.setAReadedValue(index);
        this.ticked = true;
    }

    public void setProgramm(int index) {
        this.programm_Readed.setAReadedValue(index);
        this.ticked = true;
    }

    public boolean isBankChecked() {
        return bankChecked;
    }

    public boolean isCombiChecked() {
        return combiChecked;
    }


    public void  translateProgrammTick() {
        if (programm_Readed.getSettledLength()>0)  {
           Integer[] ret = new Integer[programm_Readed.getSettledLength()];
           int i = 0;
            for (Integer tickedIndex : programm_Readed.getAllSettledIdx()) {
                ret[i] = programmTranslator[tickedIndex];
            }

           programmTick =  ret;


        } else {
            programmTick = new Integer[0];
        }



    }


    public String getMarketCode() {
        return marketCode;
    }

    public void  translateSpecials() {
        if (specials_Readed.getSettledLength()>0)  {
           Integer[] ret = new Integer[specials_Readed.getSettledLength()];
           int i = 0;
            for (Integer tickedIndex : specials_Readed.getAllSettledIdx()) {
                ret[i] = tickedIndex +1;
            }

           specials =  ret;


        } else {
            specials = new Integer[0];
        }

    }

    public void  translateKurs() {
            if (kurs_Readed.getSettledLength()>0)  {
               Integer[] ret = new Integer[kurs_Readed.getSettledLength()];
               int i = 0;
                for (Integer tickedIndex : kurs_Readed.getAllSettledIdx()) {
                    ret[i] = kursTranslator[tickedIndex];
                }

               kurs =  ret;


            } else {
                kurs = new Integer[0];
            }


        }

    public Integer[] getErgebnieswetteA() {
        return ergebnisewette_A_Readed.getAllSettledIdx();

    }

    public Integer[] getErgebnieswetteB() {
           return ergebnisewette_B_Readed.getAllSettledIdx();

       }

    public Integer[] getProgrammTick() {
        return programmTick;
    }

    public void setProgrammTick(Integer[] programmTick) {
        this.programmTick = programmTick;
    }

    public Integer[] getSpecials() {
        return specials;
    }

    public void setSpecials(Integer[] specials) {
        this.specials = specials;
    }

    public Integer[] getKurs() {
        return kurs;
    }

    public void setKurs(Integer[] kurs) {
        this.kurs = kurs;
    }

    public boolean isSyntaxValid() {
        return marketCode != null && !marketCode.equals("") && kurs != null && kurs.length>0;
    }


    /*public static void main(String[] args) throws Exception {
        MarketDetailsTranslatorClient.initialize("http://localhost:8080/stub/marketdetails/{code}");
        String jsonRet = MarketDetailsTranslatorClient.getInstance().getMarketDetailsByCode("12ABs");
        System.out.println("----------------------->>" + jsonRet);
        MarketDetail md = (MarketDetail) new JSONDeserializer().use( null, MarketDetail.class ).deserialize( jsonRet );
        System.out.println("*********************"+md);
    }*/

    public boolean expandEventFromDB() {
        try {

            String jsonRet = MarketDetailsTranslatorClient.getInstance().getMarketDetailsByCode(marketCode);
            //logger.debug(" JSON RETURNED FOR CODE {} -----> {}",marketCode,jsonRet);
            if (jsonRet != null) {
                MarketDetail md = (MarketDetail) new JSONDeserializer().use( null, MarketDetail.class ).deserialize( jsonRet );
                //logger.debug("MarketDetail parsed {}",md);
                team1 = md.getTeam1();
                team2 = md.getTeam2();
                if (kurs.length == 0) {
                    odd =  md.getOdd_result_1();
                }  else {
                    if (kurs[0] == kursTranslator[0]) {
                        odd = md.getOdd_result_1();
                    } else if (kurs[0] == kursTranslator[1]) {
                        odd = md.getOdd_result_0();
                    } else if (kurs[0] == kursTranslator[2]) {
                        odd = md.getOdd_result_2();
                    }
                }
                eventDate = md.getEventDate();
                presentOnDB = true;

            }  else {
                //logger.debug(" Scommessa non presente nello stub");
                presentOnDB = false;
            }

        } catch (Exception e) {
            logger.error("errore nella traduzione dei codici ",e);
            presentOnDB = false;
        }
        //logger.debug("Present on DB {}",presentOnDB);
            return presentOnDB;



    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public float getOdd() {
        return odd;
    }

    public boolean isPresentOnDB() {
        return presentOnDB;
    }

    public float calculateWinningAndReturnFloat(int stakeCent) {

        winningFloat = odd * stakeCent/100;
        return winningFloat;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}

