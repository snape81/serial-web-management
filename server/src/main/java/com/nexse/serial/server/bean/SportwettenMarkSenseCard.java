package com.nexse.serial.server.bean;

import java.util.Arrays;

public class SportwettenMarkSenseCard extends MarkSenseCard {

    private int stakeCents;
    private double stake;
    private boolean markSenseValid = true;

    private int totalCostCent;
    private double totalCost;
    private float totalwinning;
    private SportwettenBettedEvent[] bettedEvents = new SportwettenBettedEvent[10];


    private int[] stackCentsArray = new int[]{200000, 100000, 50000, 40000, 30000, 20000, 10000, 5000, 4000, 3000, 2500, 2000, 1500, 1000, 900, 800, 700, 600, 500, 400, 300, 200, 100, 50};


    private static final int START_SPORTWETTEN_GENERAL_INDEX = 2;
    private static final int END_SPORTWETTEN_GENERAL_INDEX = 58;


    public static final int STAKE_ROW_INDEX = 1;
    public static final int EVENT_START_ROW_INDEX = 4;
    public static final int EVENT_END_ROW_INDEX = 13;

    private static final int BANK_INDEX = 2;
    private static final int COMBI_INDEX = 58;

    private static final int START_INDEX_ERGEBNISWETTE_B = 5;
    private static final int END_INDEX_ERGEBNISWETTE_B = 11;

    private static final int START_INDEX_ERGEBNISWETTE_A = 13;
    private static final int END_INDEX_ERGEBNISWETTE_A = 19;

    private static final int START_INDEX_KURS = 22;
    private static final int END_INDEX_KURS = 24;

    private static final int START_INDEX_SPECIALS = 27;
    private static final int END_INDEX_SPECIALS = 33;

    private static final int START_INDEX_STAKE = 34;
    private static final int END_INDEX_STAKE = 57;

    public static final int START_INDEX_MARKET_CODE = 35;
    public static final int END_INDEX_MARKET_CODE = 49;

    public static final int START_INDEX_PROGRAMM = 53;
    public static final int END_INDEX_PROGRAMM = 56;

    // offsets

    public static final int OFFSET_ERGEBNISWETTE_B = START_INDEX_ERGEBNISWETTE_B + (END_INDEX_ERGEBNISWETTE_B - START_INDEX_ERGEBNISWETTE_B);
    public static final int OFFSET_ERGEBNISWETTE_A = START_INDEX_ERGEBNISWETTE_A + (END_INDEX_ERGEBNISWETTE_A - START_INDEX_ERGEBNISWETTE_A);
    public static final int OFFSET_KURS = START_INDEX_KURS + (END_INDEX_KURS - START_INDEX_KURS);
    public static final int OFFSET_SPECIALS = START_INDEX_SPECIALS + (END_INDEX_SPECIALS - START_INDEX_SPECIALS);
    public static final int OFFSET_PROGRAMM = START_INDEX_PROGRAMM + (END_INDEX_PROGRAMM - START_INDEX_PROGRAMM);
    public static final int OFFSET_MARKET_CODE = START_INDEX_MARKET_CODE + (END_INDEX_MARKET_CODE - START_INDEX_MARKET_CODE);

    public static final int EVENT_COLUMN_OFFSET = 4;


    public SportwettenMarkSenseCard(String rawDataFromScanner, Boolean valid) {
        super(rawDataFromScanner, valid, MarkSenseCard.TYPE_SPORTWETTEN);
        setTypeId(MarkSenseCard.TYPE_SPORTWETTEN);

        for (int i = 0; i < bettedEvents.length; i++) {
            bettedEvents[i] = new SportwettenBettedEvent(OFFSET_ERGEBNISWETTE_B, OFFSET_ERGEBNISWETTE_A, OFFSET_KURS, OFFSET_SPECIALS, OFFSET_PROGRAMM, OFFSET_MARKET_CODE);
        }


        if (isValid()) {
            for (int i = START_SPORTWETTEN_GENERAL_INDEX; i <= END_SPORTWETTEN_GENERAL_INDEX; i++) {
                if (getListaRighe().containsKey(i)) {
                    CardRow cr = getListaRighe().get(i);
                    boolean[] markedCells = cr.getMarkedCells();

                    if (i == BANK_INDEX) {
                        bankTicker(markedCells, i);
                    }

                    if (i == COMBI_INDEX) {
                        combiTicker(markedCells,i);
                    }

                    if (i >= START_INDEX_ERGEBNISWETTE_B && i <= END_INDEX_ERGEBNISWETTE_B ) {
                        ergebnieswetteBTicker(markedCells, i);
                    }

                    if (i >= START_INDEX_ERGEBNISWETTE_A && i <= END_INDEX_ERGEBNISWETTE_A) {
                        ergebnieswetteATicker(markedCells, i);
                    }

                    if ( i >= START_INDEX_KURS && i <= END_INDEX_KURS ) {
                        kursTicker(markedCells, i);
                    }

                    if (i >= START_INDEX_SPECIALS && i <= END_INDEX_SPECIALS) {
                        specialsTicker(markedCells, i);
                    }

                    if (i >= START_INDEX_MARKET_CODE && i <= END_INDEX_MARKET_CODE) {
                        marketCodeTicker(markedCells, i);
                    }

                    if (i >= START_INDEX_PROGRAMM && i <= END_INDEX_PROGRAMM) {
                        programmTicker(markedCells, i);
                    }

                    if (i >= START_INDEX_STAKE && i <= END_INDEX_STAKE) {
                        stakeCalculator(markedCells, i);
                    }


                }
            }



            stake = (double) stakeCents / 100;
            finalizeReading();
        }

    }

    private void finalizeReading() {
        for (SportwettenBettedEvent bettedEvent : bettedEvents) {
            bettedEvent.createMarketCode();
            bettedEvent.translateKurs();
            bettedEvent.translateProgrammTick();
            bettedEvent.translateSpecials();
            if (bettedEvent.isSyntaxValid() && bettedEvent.expandEventFromDB()) {
                totalwinning += bettedEvent.calculateWinningAndReturnFloat(stakeCents);
                totalCostCent += stakeCents;
                totalCost += stake;
            } else {
                markSenseValid = false;
            }

        }


    }

    private void specialsTicker(boolean[] markedCells, int i) {
        for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
            if (markedCells[j]) {
                bettedEvents[j - EVENT_COLUMN_OFFSET].setSpecials(i);
            }

        }
    }

    private void programmTicker(boolean[] markedCells, int i) {
        for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
            if (markedCells[j]) {
                bettedEvents[j - EVENT_COLUMN_OFFSET].setProgramm(i);
            }

        }
    }

    private void marketCodeTicker(boolean[] markedCells, int i) {
        for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
            if (markedCells[j]) {
                bettedEvents[j - EVENT_COLUMN_OFFSET].setMarket_code(i);
            }

        }
    }

    private void kursTicker(boolean[] markedCells, int i) {
        for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
            if (markedCells[j]) {
                bettedEvents[j - EVENT_COLUMN_OFFSET].setKurs(i);
            }

        }
    }

    private void ergebnieswetteATicker(boolean[] markedCells, int i) {
        for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
            if (markedCells[j]) {
                bettedEvents[j - EVENT_COLUMN_OFFSET].setErgebnisewette_A(i);
            }

        }
    }

    private void ergebnieswetteBTicker(boolean[] markedCells, int i) {
        for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
            if (markedCells[j]) {
                bettedEvents[j - EVENT_COLUMN_OFFSET].setErgebnisewette_B(i);
            }

        }
    }

    private void bankTicker(boolean[] markedCells, int i) {
        for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
            if (markedCells[j]) {
                bettedEvents[j - EVENT_COLUMN_OFFSET].setBankChecked(true);
            }

        }
    }

    private void combiTicker(boolean[] markedCells, int i) {
            for (int j = EVENT_START_ROW_INDEX; j <= EVENT_END_ROW_INDEX; j++) {
                if (markedCells[j]) {
                    bettedEvents[j - EVENT_COLUMN_OFFSET].setCombiChecked(true);
                }

            }
        }


    private void stakeCalculator(boolean[] markedCells, int i) {
        if (markedCells[STAKE_ROW_INDEX]) {
            stakeCents += getStakeInCentsByIndex(i);
        }

    }

    private int getStakeInCentsByIndex(int rowIndex) {
        int toSearch = rowIndex - START_INDEX_STAKE;
        if (toSearch >= 0 && toSearch < stackCentsArray.length) {
            return stackCentsArray[toSearch];

        } else {
            // loggare errore
            return 0;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SportwettenMarkSenseCard");
        sb.append("{stake=").append(stake);
        sb.append(", stakeCents=").append(stakeCents);
        sb.append(", totalCostCent=").append(totalCostCent);
        sb.append(", totalCost=").append(totalCost);
        sb.append(", totalwinning=").append(totalwinning);
        sb.append(",MARKET TICKED: \n");
        int i = 0;
        for (SportwettenBettedEvent bettedEvent : bettedEvents) {
            sb.append("MARKET ").append(i + 1).append("\n");
            if (bettedEvent.isTicked()) {
                sb.append("{kombi=").append(bettedEvent.isCombiChecked());
                sb.append(",programm=").append(Arrays.toString(bettedEvent.getProgrammTick()));
                sb.append(",market_code=").append(bettedEvent.getMarketCode());
                sb.append(",specials=").append(Arrays.toString(bettedEvent.getSpecials()));
                sb.append(",kurs=").append(Arrays.toString(bettedEvent.getKurs()));
                sb.append(",ergebnieswetteA=").append(Arrays.toString(bettedEvent.getErgebnieswetteA()));
                sb.append(",ergebnieswetteB=").append(Arrays.toString(bettedEvent.getErgebnieswetteB()));
                sb.append(",bank=").append(bettedEvent.isBankChecked());
                sb.append(",syntaxValid=").append(bettedEvent.isSyntaxValid());
                sb.append(",presentOnDb=").append(bettedEvent.isPresentOnDB());
                sb.append(",team1=").append(bettedEvent.getTeam1());
                sb.append(",team2=").append(bettedEvent.getTeam2());
                sb.append(",odd=").append(bettedEvent.getOdd());
            } else {
                sb.append("{ UNCHECKED }");
            }
            sb.append("\n\n");
            i++;
        }
        sb.append('}');
        return sb.toString();
    }

    public SportwettenBettedEvent[] getBettedEvents() {
        return bettedEvents;
    }

    public double getStake() {
        return stake;
    }

    public void setStake(double stake) {
        this.stake = stake;
    }

    public int getStakeCents() {
        return stakeCents;
    }

    public void setStakeCents(int stakeCents) {
        this.stakeCents = stakeCents;
    }

    public boolean isMarkSenseValid() {
        return markSenseValid;
    }

    public void setMarkSenseValid(boolean markSenseValid) {
        this.markSenseValid = markSenseValid;
    }

    public int getTotalCostCent() {
        return totalCostCent;
    }

    public void setTotalCostCent(int totalCostCent) {
        this.totalCostCent = totalCostCent;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public float getTotalwinning() {
        return totalwinning;
    }

    public void setTotalwinning(float totalwinning) {
        this.totalwinning = totalwinning;
    }
}
