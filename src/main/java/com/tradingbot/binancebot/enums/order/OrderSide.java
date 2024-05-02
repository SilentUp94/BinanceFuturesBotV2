package com.tradingbot.binancebot.enums.order;

public enum OrderSide {

    BUY("BUY"),
    SELL("SELL");
    public final String text;

    OrderSide(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
