package com.tradingbot.binancebot.orders;


import com.tradingbot.binancebot.api.IClient;


public interface OrderInterface extends IClient {
     void loadOpeningParams();
     String openOrder();
}
