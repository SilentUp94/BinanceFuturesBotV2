package com.tradingbot.binancebot.api;

import com.binance.api.client.impl.BinanceApiAsyncRestClientImpl;
import com.binance.api.client.impl.BinanceApiRestClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;

public interface IClient {
    CustomBinanceClient binanceClient = CustomBinanceClient.getInstance();
    UMFuturesClientImpl futuresClient = binanceClient.getFuturesClient();
    BinanceApiAsyncRestClientImpl restClient = binanceClient.getRestClient();
    UMWebsocketClientImpl websocketClient = binanceClient.getWebSocketClient();


}
