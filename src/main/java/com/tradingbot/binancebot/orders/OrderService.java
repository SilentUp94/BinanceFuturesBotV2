package com.tradingbot.binancebot.orders;

import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.tradingbot.binancebot.api.CustomBinanceClient;
import com.tradingbot.binancebot.api.IClient;
import com.tradingbot.binancebot.enums.LocalFileName;
import com.tradingbot.binancebot.util.Services;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class OrderService implements IClient {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public void closeAllOrders(String symbol){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol",symbol);
        try {
            String result = futuresClient.account().cancelAllOpenOrders(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
    }



    public String closeOrderById(Long orderId, String symbol){
        LinkedHashMap<String,Object> params = new LinkedHashMap<>();
        params.put("orderId",orderId);
        params.put("symbol",symbol);
        String result = null;
        try {
            result = futuresClient.account().cancelOrder(params);
            Services.writeToFile("Close trade: ", result, LocalFileName.TRADES );
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public ProcessedOrder createProcessedOrder(String result){
        JSONObject jsonObject = new JSONObject(result);
        ProcessedOrder processedOrder = new ProcessedOrder();
        processedOrder.setResult(result);
        Class<?> myClass = processedOrder.getClass();
        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Object value = jsonObject.get(fieldName);

                Class<?> fieldType = field.getType();
                if (fieldType == String.class) {
                    field.set(processedOrder, (String) value);
                } else if (fieldType == Long.class) {
                    field.set(processedOrder, (Long)value);
                } else if (fieldType == Double.class) {
                    field.set(processedOrder,Double.parseDouble(value.toString()));
                } else if (fieldType == Boolean.class) {
                    field.set(processedOrder, (boolean) value);
                }
            }
            catch (JSONException e) {
                System.out.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.out.println("Illegal access");
            }
        }
        return processedOrder;
    }

    public String getQueryOrder(String symbol,Long orderId){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("orderId", orderId);
        String result = null;
        try {
            result = futuresClient.account().queryOrder(parameters);
            //logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }







}
