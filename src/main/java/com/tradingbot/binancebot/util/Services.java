package com.tradingbot.binancebot.util;

import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.tradingbot.binancebot.api.CustomBinanceClient;
import com.tradingbot.binancebot.api.IClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DecimalNum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class Services implements IClient {

    private static final Logger logger = LoggerFactory.getLogger(Services.class);


    public Services() {
    }

    public static void writeToFile(String header, String message, String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));

            out.write( header + "\n" +message + "\n");
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFile(String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));
            out.flush();
            out.close();
            System.out.println("File cleared!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Bar createBar(String event) {
        JSONObject json = new JSONObject(event);
        JSONObject kline = json.getJSONObject("k");
        DecimalNum openPrice = DecimalNum.valueOf(kline.getString("o"));
        DecimalNum highPrice = DecimalNum.valueOf(kline.getString("h"));
        DecimalNum lowPrice = DecimalNum.valueOf(kline.getString("l"));
        DecimalNum closePrice = DecimalNum.valueOf(kline.getString("c"));
        DecimalNum volume = DecimalNum.valueOf(kline.getString("v"));
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(json.getLong("E")), ZoneId.systemDefault());
        return BaseBar.builder()
                .timePeriod(Duration.ofMinutes(1))
                .endTime(dateTime)
                .openPrice(openPrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .closePrice(closePrice)
                .volume(volume).build();
    }

    public static Double rightFormat(Double number, String format) {

        DecimalFormat decimalFormat = new DecimalFormat(format);
        String nr = decimalFormat.format(number);
        return Double.parseDouble(nr);
    }
    public LinkedHashMap<String, Object> getClassParameters(Object object) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Class<?> myClass = object.getClass();
        Class<?> mySuperClass = myClass.getSuperclass();
        Field[] fields = mySuperClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                    if (field.get(object) != null) {
                        parameters.put(field.getName(), field.get(object).toString());
                    }
            } catch (IllegalAccessException e) {
                System.out.println("error");
            }
        }
        return parameters;
    }

    public static Object extractParamFromJSON(String param, String jsonFile){
        JSONObject jsonObject = new JSONObject(jsonFile);
        return jsonObject.get(param);
    }

    public static Double getTickSize(String checkSymbol){
        String result = getExchangeInfo();
        Double tickSize = null;
        try {
            JSONObject lvl1 = new JSONObject(result);
            JSONArray symbolsArray = (JSONArray) lvl1.get("symbols");
            for (Object o : symbolsArray) {
                JSONObject symbol = (JSONObject) o;
                String symbolName = (String) symbol.get("symbol");
                if(symbolName.equals(checkSymbol)){
                    JSONArray filters = (JSONArray) symbol.get("filters");
                    JSONObject priceFilter = (JSONObject) filters.get(0);
                    String tick = (String) priceFilter.get("tickSize");
                    tickSize = Double.parseDouble(tick);
                    break;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return tickSize;
    }

    public static String getExchangeInfo(){
        String result = null;
        try {
            result = futuresClient.market().exchangeInfo();
            //logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

//    public String extractFromJason(String string,String parameter) {
//        JSONObject json = new JSONObject(event);
//        JSONObject kline = json.getJSONObject("k");
//        DecimalNum openPrice = DecimalNum.valueOf(kline.getString("o"));
//        DecimalNum highPrice = DecimalNum.valueOf(kline.getString("h"));
//        DecimalNum lowPrice = DecimalNum.valueOf(kline.getString("l"));
//        DecimalNum closePrice = DecimalNum.valueOf(kline.getString("c"));
//        DecimalNum volume = DecimalNum.valueOf(kline.getString("v"));
//        ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(json.getLong("E")), ZoneId.systemDefault());
//        return "";
//    }

}
