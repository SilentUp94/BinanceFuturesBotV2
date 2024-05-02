package com.tradingbot.binancebot.account_information;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.tradingbot.binancebot.client_account.ConfigProject;

public class Test {
    private static final ConfigProject CONFIG = ConfigProject.getInstance();
    public static void main(String[] args) {
        UMFuturesClientImpl futuresClient = new UMFuturesClientImpl(CONFIG.getTESTNET_API_KEY(),
                CONFIG.getTESTNET_SECRET_KEY(), CONFIG.getTESTNET_BASE_URL());

        String listenKey = futuresClient.userData().createListenKey();

        UMWebsocketClientImpl client = new UMWebsocketClientImpl();

        client.listenUserStream(listenKey, ((event) -> {
            System.out.println("event: " + event);
            //client.closeAllConnections();
        }));


    }
}
