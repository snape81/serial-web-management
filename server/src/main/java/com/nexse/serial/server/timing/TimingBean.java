package com.nexse.serial.server.timing;

import java.util.ArrayList;
import java.util.Collections;

public class TimingBean implements Comparable {

    private Integer marksenseProgr;
    private long scannerReadCompleted;
    private long readValidationCompleted;
    private long afterTransferringToWebsocketBuffer;
    private long afterRejectInGoodTray;
    private long preRejectInGoodTray;
    private long preSuperclassSportwetteInstantiation;
    private long postSuperclassSportwetteInstantiation;
    private long preBettedEventsCreation;
    private long postBettedEventsCreation;
    private long preSemanticCalc;
    private long postSemanticCalc;
    private long preFinalizeReading;
    private long postFinalizingReading;
    private long receivedPrintingCommandByWebSocket;
    private long executedPrintingCommandByWebSocket;
    private long scannerDataReadedByWebSocket;
    private long afterJsonSerialization;
    private long afterSendOnTheChannel;
    private long preSportwettenInstantiation;
    private String msccode;


    public ArrayList<TimeDetail> getATimeDetailsList() {
        ArrayList<TimeDetail> tdlist = new ArrayList<>();
        tdlist.add(new TimeDetail("scannerReadCompleted", scannerReadCompleted, 0));
        tdlist.add(new TimeDetail("readValidationCompleted", readValidationCompleted, 0));
        tdlist.add(new TimeDetail("afterTransferringToWebsocketBuffer", afterTransferringToWebsocketBuffer, 0));
        tdlist.add(new TimeDetail("afterRejectInGoodTray", afterRejectInGoodTray, 0));
        tdlist.add(new TimeDetail("preRejectInGoodTray", preRejectInGoodTray, 0));
        tdlist.add(new TimeDetail("preSuperclassSportwetteInstantiation", preSuperclassSportwetteInstantiation, 0));
        tdlist.add(new TimeDetail("postSuperclassSportwetteInstantiation", postSuperclassSportwetteInstantiation, 0));
        tdlist.add(new TimeDetail("preBettedEventsCreation", preBettedEventsCreation, 0));
        tdlist.add(new TimeDetail("postBettedEventsCreation", postBettedEventsCreation, 0));
        tdlist.add(new TimeDetail("preSemanticCalc", preSemanticCalc, 0));
        tdlist.add(new TimeDetail("postSemanticCalc", postSemanticCalc, 0));
        tdlist.add(new TimeDetail("preFinalizeReading", preFinalizeReading, 0));
        tdlist.add(new TimeDetail("postFinalizingReading", postFinalizingReading, 0));
        tdlist.add(new TimeDetail("receivedPrintingCommandByWebSocket", receivedPrintingCommandByWebSocket, 0));
        tdlist.add(new TimeDetail("executedPrintingCommandByWebSocket", executedPrintingCommandByWebSocket, 0));
        tdlist.add(new TimeDetail("scannerDataReadedByWebSocket", scannerDataReadedByWebSocket, 0));
        tdlist.add(new TimeDetail("afterJsonSerialization", afterJsonSerialization, 0));
        tdlist.add(new TimeDetail("afterSendOnTheChannel", afterSendOnTheChannel, 0));
        tdlist.add(new TimeDetail("preSportwettenInstantiation", preSportwettenInstantiation, 0));
        Collections.sort(tdlist);
        long lastnanos = tdlist.get(0).nanos;
        for (TimeDetail timeDetail : tdlist) {
            timeDetail.delta = timeDetail.nanos -lastnanos;
            lastnanos = timeDetail.nanos;
        }

        return tdlist;
    }

    public Integer getMarksenseProgr() {
        return marksenseProgr;
    }

    public void setMarksenseProgr(Integer marksenseProgr) {
        this.marksenseProgr = marksenseProgr;
    }

    public long getScannerReadCompleted() {
        return scannerReadCompleted;
    }

    public void setScannerReadCompleted() {
        this.scannerReadCompleted = System.nanoTime();
    }

    public long getReadValidationCompleted() {
        return readValidationCompleted;
    }

    public void setReadValidationCompleted() {
        this.readValidationCompleted = System.nanoTime();
    }

    public long getAfterTransferringToWebsocketBuffer() {
        return afterTransferringToWebsocketBuffer;
    }

    public void setAfterTransferringToWebsocketBuffer() {
        this.afterTransferringToWebsocketBuffer = System.nanoTime();
    }

    public long getAfterRejectInGoodTray() {
        return afterRejectInGoodTray;
    }

    public void setAfterRejectInGoodTray() {
        this.afterRejectInGoodTray = System.nanoTime();
    }

    public long getPreRejectInGoodTray() {
        return preRejectInGoodTray;
    }

    public void setPreRejectInGoodTray() {
        this.preRejectInGoodTray = System.nanoTime();
    }

    public long getPreSuperclassSportwetteInstantiation() {
        return preSuperclassSportwetteInstantiation;
    }

    public void setPreSuperclassSportwetteInstantiation() {
        this.preSuperclassSportwetteInstantiation = System.nanoTime();
    }

    public long getPostSuperclassSportwetteInstantiation() {
        return postSuperclassSportwetteInstantiation;
    }

    public void setPostSuperclassSportwetteInstantiation() {
        this.postSuperclassSportwetteInstantiation = System.nanoTime();
    }

    public long getPreBettedEventsCreation() {
        return preBettedEventsCreation;
    }

    public void setPreBettedEventsCreation() {
        this.preBettedEventsCreation = System.nanoTime();
    }

    public long getPostBettedEventsCreation() {
        return postBettedEventsCreation;
    }

    public void setPostBettedEventsCreation() {
        this.postBettedEventsCreation = System.nanoTime();
    }

    public long getPreSemanticCalc() {
        return preSemanticCalc;
    }

    public void setPreSemanticCalc() {
        this.preSemanticCalc = System.nanoTime();
    }

    public long getPostSemanticCalc() {
        return postSemanticCalc;
    }

    public void setPostSemanticCalc() {
        this.postSemanticCalc = System.nanoTime();
    }

    public long getPreFinalizeReading() {
        return preFinalizeReading;
    }

    public void setPreFinalizeReading() {
        this.preFinalizeReading = System.nanoTime();
    }

    public long getPostFinalizingReading() {
        return postFinalizingReading;
    }

    public void setPostFinalizingReading() {
        this.postFinalizingReading = System.nanoTime();
    }

    public long getReceivedPrintingCommandByWebSocket() {
        return receivedPrintingCommandByWebSocket;
    }

    public void setReceivedPrintingCommandByWebSocket() {
        this.receivedPrintingCommandByWebSocket = System.nanoTime();
    }

    public long getExecutedPrintingCommandByWebSocket() {
        return executedPrintingCommandByWebSocket;
    }

    public void setExecutedPrintingCommandByWebSocket() {
        this.executedPrintingCommandByWebSocket = System.nanoTime();
    }

    public long getScannerDataReadedByWebSocket() {
        return scannerDataReadedByWebSocket;
    }

    public void setScannerDataReadedByWebSocket() {
        this.scannerDataReadedByWebSocket = System.nanoTime();
    }

    public long getAfterJsonSerialization() {
        return afterJsonSerialization;
    }

    public void setAfterJsonSerialization() {
        this.afterJsonSerialization = System.nanoTime();
    }

    public long getAfterSendOnTheChannel() {
        return afterSendOnTheChannel;
    }

    public void setAfterSendOnTheChannel() {
        this.afterSendOnTheChannel = System.nanoTime();
    }

    public long getPreSportwettenInstantiation() {
        return preSportwettenInstantiation;
    }

    public void setPreSportwettenInstantiation() {
        this.preSportwettenInstantiation = System.nanoTime();
    }


    @Override
    public int compareTo(Object o) {
        TimingBean other = (TimingBean) o;
        return Integer.compare(this.getMarksenseProgr(), other.getMarksenseProgr());
    }

    public void setMSCCode(String bscode) {
        this.msccode = bscode;
    }

    public String getMsccode() {
        return msccode;
    }
}
