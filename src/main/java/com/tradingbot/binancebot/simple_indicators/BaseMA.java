package com.tradingbot.binancebot.simple_indicators;

import com.tradingbot.binancebot.api.IClient;
import com.tradingbot.binancebot.enums.CalcType;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

import java.time.Duration;

public abstract class BaseMA implements IClient {
    public abstract Double calculate(Double initialValue, BarSeries barSeries, Integer period, CalcType calculationType);
    public abstract boolean isValidBar(String event);
    public void addCandle(Bar bar, BarSeries barSeries){
        barSeries.addBar(bar);
        System.out.println("New candle added!");
    }
    public abstract void perform(String event);
}
