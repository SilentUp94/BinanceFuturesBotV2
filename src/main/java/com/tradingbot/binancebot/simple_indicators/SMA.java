package com.tradingbot.binancebot.simple_indicators;

import com.tradingbot.binancebot.enums.CalcType;
import com.tradingbot.binancebot.enums.LocalFileName;
import com.tradingbot.binancebot.util.Services;
import org.json.JSONObject;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import java.util.Scanner;

public class SMA extends BaseMA {
    private Services services = new Services();
    private Double smaValue;
    private Integer period;
    private BarSeries series = new BaseBarSeries();
    private Scanner scanner = new Scanner(System.in);
    private CalcType calcType;

    public SMA(Integer period, CalcType calcType) {
        System.out.println("SMA" + period + " initial value: ");
        this.smaValue = (Double.parseDouble(scanner.nextLine()));
        this.calcType = calcType;
        this.period = period;
    }

    public Double getSmaValue() {
        return smaValue;
    }

    private void setSmaValue(Double smaValue) {
        this.smaValue = smaValue;
    }

    @Override
    public void perform(String event) {

        if (isValidBar(event)) {
            Bar candle = services.createBar(event);
            addCandle(candle, series);
            if (series.getBarCount() != 1) {
                setSmaValue(calculate(getSmaValue(), series, period, calcType));
            }
        }

    }

    @Override
    public Double calculate(Double currentSmaValue, BarSeries barSeries, Integer period, CalcType calculationType) {
        double currentPrice = 0.0;
        if (calculationType.equals(CalcType.OPEN_PRICE)) {
            currentPrice = barSeries.getLastBar().getOpenPrice().doubleValue();
        } else if (calculationType.equals(CalcType.CLOSE_PRICE)) {
            currentPrice = barSeries.getLastBar().getClosePrice().doubleValue();
        }

        return (currentSmaValue * (period - 1) + currentPrice) / period;
    }

    @Override
    public boolean isValidBar(String event) {
        JSONObject json = new JSONObject(event);
        JSONObject kline = json.getJSONObject("k");
        return kline.getBoolean("x");
    }
}
