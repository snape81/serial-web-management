package com.nexse.serial.server.bean;

public class SportwettenBettedEvent {

    private int[] kursTranslator = new int[]{1,0,2};
    private String[] marketCodeTranslator = new String[]{"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    private int[] programmTranslator = new int[]{1000,100,10,1};

        private static final int ERGENISWETTE_THICK_NUMBER = 7;
        private static final int SPECIALS_THICK_NUMBER = 7;
        private static final int KURS_THICK_NUMBER = 3;
        private static final int PROGRAMM_THICK_NUMBER = 4;
        private static final int MARKET_CODE_THICK_NUMBER = 15;

        private ArrayValueContainer ergebnisewette_A_Readed;

        private ArrayValueContainer ergebnisewette_B_Readed;

        private ArrayValueContainer kurs_Readed;

        private ArrayValueContainer specials_Readed;

        private ArrayValueContainer market_code_Readed;

        private ArrayValueContainer programm_Readed;

        private String marketCode;

        private boolean combiChecked;

        private boolean bankChecked;

        private boolean ticked;

    public SportwettenBettedEvent(int offsetErgebniswetteB, int offsetErgebniswetteA, int offsetKurs, int offsetSpecials, int offsetProgramm,int offset_market_code) {
             ticked = false;
             combiChecked = false;
             bankChecked = false;
             marketCode = "";
             ergebnisewette_A_Readed = new ArrayValueContainer(ERGENISWETTE_THICK_NUMBER,offsetErgebniswetteA);
             ergebnisewette_B_Readed = new ArrayValueContainer(ERGENISWETTE_THICK_NUMBER,offsetErgebniswetteB);
             kurs_Readed = new ArrayValueContainer(KURS_THICK_NUMBER,offsetErgebniswetteA);
             specials_Readed = new ArrayValueContainer(SPECIALS_THICK_NUMBER,offsetSpecials);
             market_code_Readed = new ArrayValueContainer(MARKET_CODE_THICK_NUMBER,offset_market_code);
             programm_Readed = new ArrayValueContainer(PROGRAMM_THICK_NUMBER,offsetProgramm);
        }

    public void createMarketCode() {

        if (market_code_Readed.getSettledLength() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Integer tickedIndex : programm_Readed.getAllSettledIdx()) {
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

    public Integer[] getCheckedErgebnisewette_B() {
       return  ergebnisewette_B_Readed.getAllSettledIdx();
    }

    public boolean isBankChecked() {
        return bankChecked;
    }

    public boolean isCombiChecked() {
        return combiChecked;
    }


    public Integer[] getProgrammTick() {
        if (programm_Readed.getSettledLength()>0)  {
           Integer[] ret = new Integer[programm_Readed.getSettledLength()];
           int i = 0;
            for (Integer tickedIndex : programm_Readed.getAllSettledIdx()) {
                ret[i] = programmTranslator[tickedIndex];
            }

           return  ret;


        } else {
            return new Integer[0];
        }



    }


    public String getMarketCode() {
        return marketCode;
    }

    public Integer[]  getSpecials() {
        if (specials_Readed.getSettledLength()>0)  {
           Integer[] ret = new Integer[specials_Readed.getSettledLength()];
           int i = 0;
            for (Integer tickedIndex : specials_Readed.getAllSettledIdx()) {
                ret[i] = tickedIndex +1;
            }

           return  ret;


        } else {
            return new Integer[0];
        }

    }

    public Integer[]  getKurs() {
            if (kurs_Readed.getSettledLength()>0)  {
               Integer[] ret = new Integer[kurs_Readed.getSettledLength()];
               int i = 0;
                for (Integer tickedIndex : kurs_Readed.getAllSettledIdx()) {
                    ret[i] = kursTranslator[tickedIndex];
                }

               return  ret;


            } else {
                return new Integer[0];
            }

        }

    public Integer[] getErgebnieswetteA() {
        return ergebnisewette_A_Readed.getAllSettledIdx();

    }

    public Integer[] getErgebnieswetteB() {
           return ergebnisewette_B_Readed.getAllSettledIdx();

       }
}

