package com.tradingbot.binancebot.orders;

import com.binance.api.client.domain.account.NewOrder;
import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.tradingbot.binancebot.enums.order.*;
import com.tradingbot.binancebot.util.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;


public class CreateNewOrder extends EntityOrder implements OrderInterface{
    private final Services services = new Services();
    private static final Logger logger = LoggerFactory.getLogger(CreateNewOrder.class);
    private LinkedHashMap<String,Object> parameters;


    public CreateNewOrder() {
        super();
    }
    public CreateNewOrder(OrderSide side, OrderType orderType) {
        this.setType(orderType);
        this.setSide(side);
    }


    public LinkedHashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(LinkedHashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void loadOpeningParams() {
        this.parameters = new LinkedHashMap<>();
        this.parameters = services.getClassParameters(this);
    }
    @Override
    public String openOrder() {
        loadOpeningParams();
        String result = null;
        try {
            result = futuresClient.account().newOrder(getParameters());
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
            throw e;
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
            throw e;
        }
        return result;
    }
}
