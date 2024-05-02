package com.tradingbot.binancebot.simple_indicators;

import com.tradingbot.binancebot.enums.CalcType;
import com.tradingbot.binancebot.enums.LocalFileName;
import com.tradingbot.binancebot.util.Services;
import org.json.JSONObject;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.num.DecimalNum;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EMA extends BaseMA {
    private BarSeries series = new BaseBarSeries();

    private Services services = new Services();
    private Double emaValue;
    private Integer period;
    private CalcType calcType;

    private boolean isValid;
    private String previousEvent = "";
    private Boolean isNewTimeFrameEvent = false;
    private Double nextEmaValue;

    public Double getNextEmaValue() {
        return nextEmaValue;
    }

    public void setNextEmaValue(Double nextEmaValue) {
        this.nextEmaValue = nextEmaValue;
    }

    private final BaseMAService baseMAService = new BaseMAService();

    public Boolean getNewTimeFrameEvent() {
        return isNewTimeFrameEvent;
    }

    public void setNewTimeFrameEvent(Boolean newTimeFrameEvent) {
        isNewTimeFrameEvent = newTimeFrameEvent;
    }

    private List<Double> emaValuesList = new ArrayList<>();
    private Scanner scanner;

    public EMA(Integer period, CalcType calcType) {
        scanner = new Scanner(System.in);
        System.out.println("EMA" + period + " current value: ");
        this.emaValue = (Double.parseDouble(scanner.nextLine()));
        this.emaValuesList.add(emaValue);
//        scanner.close();
        this.calcType = calcType;
        this.period = period;
    }

    public Double getEmaValue() {
        return emaValue;
    }

    public void setEmaValue(Double emaValue) {
        this.emaValue = emaValue;
    }

    public List<Double> getEmaValuesList() {
        return emaValuesList;
    }


    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Override
    public Double calculate(Double currentEmaValue, BarSeries barSeries, Integer period, CalcType calculationType) {
        double weightingFactor = 2.0 / (period + 1);
        double currentPrice = 0.0;
        if (calculationType.equals(CalcType.OPEN_PRICE)) {
            currentPrice = barSeries.getLastBar().getOpenPrice().doubleValue();
        } else if (calculationType.equals(CalcType.CLOSE_PRICE)) {
            currentPrice = barSeries.getLastBar().getClosePrice().doubleValue();
        }
//        System.out.println("current value : " + currentEmaValue + "  current price : " + currentPrice);
        double emaValue = weightingFactor * currentPrice + (1 - weightingFactor) * currentEmaValue;
        String emaValueFormatted = String.format("%.2f", emaValue);
        return Double.parseDouble(emaValueFormatted);
    }

    @Override
    public boolean isValidBar(String event) {
        setValid(false);
        JSONObject json = new JSONObject(event);
        JSONObject kline = json.getJSONObject("k");
        setValid(kline.getBoolean("x"));
        return kline.getBoolean("x");
    }

    @Override
    public void perform(String event) {

        Bar candle = services.createBar(event);

        if (isValidBar(event)) {
            setNewTimeFrameEvent(false);
            addCandle(candle, series);
            if (series.getBarCount() > 1) {
                setEmaValue(calculate(getEmaValue(), series, period, calcType));
                emaValuesList.add(getEmaValue());
            }
        } else {
            if (previousEvent.isEmpty()) {
                previousEvent = event;
            } else {
                if (baseMAService.isNewTimeFrameEvent(previousEvent, event)) {
                    nextEmaValue = getNextEma(candle);
                    setNewTimeFrameEvent(true);
                } else {
                    setNewTimeFrameEvent(false);
                }
                previousEvent = event;
            }
        }
    }

    private Double getNextEma(Bar candle) {
        double weightingFactor = 2.0 / (period + 1);
        double currentPrice = 0.0;
        if (calcType.equals(CalcType.OPEN_PRICE)) {
            currentPrice = candle.getOpenPrice().doubleValue();
        } else if (calcType.equals(CalcType.CLOSE_PRICE)) {
            currentPrice = candle.getClosePrice().doubleValue();
        }
//        System.out.println("current value : " + currentEmaValue + "  current price : " + currentPrice);
        double emaValue = weightingFactor * currentPrice + (1 - weightingFactor) * getEmaValue();
        String emaValueFormatted = String.format("%.2f", emaValue);
        return Double.parseDouble(emaValueFormatted);
    }
}
