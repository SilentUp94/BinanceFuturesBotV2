package com.tradingbot.binancebot.orders;

import com.tradingbot.binancebot.enums.order.TimeInForce;
import com.tradingbot.binancebot.util.Services;

public class OrderSettings {
    private OrderSettings() {
    }
    public static final String SYMBOL = "BTCUSDT";
    public static final TimeInForce TIME_IN_FORCE = TimeInForce.GTC;
    public static final Double QUANTITY = 0.001;
    public static final Double tickSize = Services.getTickSize(SYMBOL);


}
