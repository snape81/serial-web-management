package com.nexse.serial.server.printer;

import com.nexse.serial.server.bean.MarkSenseCard;

public class LastMarkSenseRead {

    private MarkSenseCard card;

    private static LastMarkSenseRead instance;

    private LastMarkSenseRead() {
    }

    public static LastMarkSenseRead getInstance() {
        if (instance == null) {
            instance = new LastMarkSenseRead();
        }
        return instance;
    }

    public MarkSenseCard getCard() {
        return card;
    }

    public void setCard(MarkSenseCard card) {
        this.card = card;
    }
}
