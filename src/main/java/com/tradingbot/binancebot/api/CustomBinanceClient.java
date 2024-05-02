package com.tradingbot.binancebot.api;

import com.binance.api.client.impl.BinanceApiAsyncRestClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.tradingbot.binancebot.client_account.ConfigProject;

public class CustomBinanceClient {

    private static final ConfigProject CONFIG = ConfigProject.getInstance();
    private static CustomBinanceClient INSTANCE; //= new CustomBinanceClient();

    private UMFuturesClientImpl futuresClient;
    private BinanceApiAsyncRestClientImpl restClient;
    private UMWebsocketClientImpl websocketClient;


//    private static final UMFuturesClientImpl CLIENT = new UMFuturesClientImpl(CONFIG.getTESTNET_API_KEY(),
//            CONFIG.getTESTNET_SECRET_KEY(), CONFIG.getTESTNET_BASE_URL());

    //testnet
//    private CustomBinanceClient(){
//        futuresClient = new UMFuturesClientImpl(CONFIG.getTESTNET_API_KEY(),
//                CONFIG.getTESTNET_SECRET_KEY(), CONFIG.getTESTNET_BASE_URL());
////        restClient = new BinanceApiAsyncRestClientImpl(CONFIG.getTESTNET_API_KEY(),
////                CONFIG.getTESTNET_SECRET_KEY());
//        websocketClient = new UMWebsocketClientImpl(CONFIG.getTESTNET_WSS_URL());
//    }

    //realnet
    private CustomBinanceClient(){
        futuresClient = new UMFuturesClientImpl(CONFIG.getAPI_KEY(),
                CONFIG.getSECRET_KEY(), CONFIG.getUM_BASE_URL());
//        restClient = new BinanceApiAsyncRestClientImpl(CONFIG.getTESTNET_API_KEY(),
//                CONFIG.getTESTNET_SECRET_KEY());
        websocketClient = new UMWebsocketClientImpl(CONFIG.getWSS_URL());
    }
    public static synchronized CustomBinanceClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomBinanceClient();
        }
        return INSTANCE;
    }
    public void BinanceClientReset(){
        INSTANCE = new CustomBinanceClient();
    }

    public UMFuturesClientImpl getFuturesClient() {
        return futuresClient;
    }

    public BinanceApiAsyncRestClientImpl getRestClient() {
        return restClient;
    }

    public UMWebsocketClientImpl getWebSocketClient() {
        return websocketClient;
    }

}
