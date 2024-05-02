package com.tradingbot.binancebot.simple_indicators;

import org.json.JSONObject;

public class BaseMAService {

    public Boolean isNewTimeFrameEvent(String lastEvent, String actualEvent){
        JSONObject jsonLastEvent = new JSONObject(lastEvent);
        JSONObject klineLastEvent = jsonLastEvent.getJSONObject("k");
        Double lastEventOpenPrice = klineLastEvent.getDouble("o");

        JSONObject jsonActualEvent = new JSONObject(actualEvent);
        JSONObject klineActualEvent = jsonActualEvent.getJSONObject("k");
        Double actualEventOpenPrice = klineActualEvent.getDouble("o");

        return !lastEventOpenPrice.equals(actualEventOpenPrice);

    }
}
