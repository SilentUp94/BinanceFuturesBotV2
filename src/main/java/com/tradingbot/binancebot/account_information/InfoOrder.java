package com.tradingbot.binancebot.account_information;


import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.tradingbot.binancebot.api.IClient;
import com.tradingbot.binancebot.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;

public class InfoOrder implements IClient {
    private static final Logger logger = LoggerFactory.getLogger(InfoOrder.class);

    public String getInfoPositions(String symbol, Long orderId) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol",symbol);

        parameters.put("orderId",orderId);

        String result = null;
        try {
            result = futuresClient.account().positionInformation(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public String getQueryOrder(String symbol,Long orderId){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("orderId", orderId);
        String result = null;
        try {
            result = futuresClient.account().queryOrder(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public String getCurrentAllOpenOrders(String symbol){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        //parameters.put("orderId",orderId);
        parameters.put("symbol", symbol);
        String result = null;
        try {
             result = futuresClient.account().currentAllOpenOrders(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public String getAllOrders(String symbol){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        String result = null;

        try {
            result = futuresClient.account().allOrders(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public String getQueryCurrentOpenOrder(String symbol, Long orderId){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("orderId", orderId);
        String result = null;
        try {
            result = futuresClient.account().queryCurrentOpenOrder(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public String getExchangeInfo(){
        String result = null;
        try {
            result = futuresClient.market().exchangeInfo();
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public void useListenUserStream(){
        //String listenKey = futuresClient.userData().createListenKey();
        String listenKey = Helper.showInputField("enter orderId");
        System.out.println("from info order listen key: " + listenKey);
        websocketClient.listenUserStream(listenKey,((event) -> {
            System.out.println("Event: " + event);
            websocketClient.closeAllConnections();
        }));
    }
    public void candlestickList(String symbol, CandlestickInterval candlestickInterval,Integer lookingBack){

        restClient.getCandlestickBars(symbol,candlestickInterval,lookingBack,null,null, this::showList) ;
    }

    public void showList(List<Candlestick> candlestickList){
        candlestickList.forEach(System.out::println);
    }

}
