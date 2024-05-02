package com.tradingbot.binancebot.strategy.grannyStrategy;

import com.tradingbot.binancebot.enums.CalcType;
import com.tradingbot.binancebot.simple_indicators.EMA;

public class GrannyStrategySettings {

    //strategy settings
    private EMA emaLow = new EMA(9, CalcType.OPEN_PRICE);
    private EMA emaHigh = new EMA(21, CalcType.OPEN_PRICE);

    private final double percentageTP = 0.5;
    private final double percentageSL = 0.4;

    //strategy settings


    public GrannyStrategySettings() {
    }

    public EMA getEmaLow() {
        return emaLow;
    }

    public EMA getEmaHigh() {
        return emaHigh;
    }

    public double getPercentageTP() {
        return percentageTP;
    }

    public double getPercentageSL() {
        return percentageSL;
    }
}
