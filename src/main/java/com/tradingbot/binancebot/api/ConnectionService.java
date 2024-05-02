package com.tradingbot.binancebot.api;

import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConnectionService implements IClient{


    //private final String listenKey;// = futuresClient.userData().createListenKey();
    private static final Logger logger = LoggerFactory.getLogger(ConnectionService.class);
    private ScheduledExecutorService scheduler;

    private static String listenKey;
    public ConnectionService() {
    }

    public void keepConnectionAlive(){
        //System.out.println("Listen Key : " + listenKey);
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::pingApi, 0, 30, TimeUnit.MINUTES);

    }
    private void pingApi(){
        String result = " ";
        try {
            listenKey = new JSONObject(futuresClient.userData().createListenKey()).getString("listenKey");
            System.out.println("Listen Key : " + listenKey);
            System.out.println("Connection extending...BEGIN!");
            result = futuresClient.userData().extendListenKey();
            System.out.println("Connection extending...SUCCESS!");
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        System.out.println("Result : " + result);
    }

    public static String getListenKey() {
        return listenKey;
    }
}
