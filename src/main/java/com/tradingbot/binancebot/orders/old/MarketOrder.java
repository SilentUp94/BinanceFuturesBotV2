package com.tradingbot.binancebot.orders.old;

import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.tradingbot.binancebot.enums.order.OrderSide;
import com.tradingbot.binancebot.enums.order.OrderType;
import com.tradingbot.binancebot.orders.EntityOrder;
import com.tradingbot.binancebot.orders.OrderInterface;
import com.tradingbot.binancebot.util.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public class MarketOrder extends EntityOrder implements OrderInterface {
    private final Services services = new Services();
    private static final Logger logger = LoggerFactory.getLogger(MarketOrder.class);
    private String result;
    private Long orderId;
    private LinkedHashMap<String,Object> parameters;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LinkedHashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(LinkedHashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    public MarketOrder(OrderSide side) {
        this.setSide(side);
        this.setType(OrderType.MARKET);
    }

    @Override
    public void loadOpeningParams() {
        this.parameters = new LinkedHashMap<>();
        this.parameters = services.getClassParameters(this);
    }

    @Override
    public String openOrder() {
        loadOpeningParams();
        String resultAction = "";
        try {
            String result = futuresClient.account().newOrder(getParameters());
            setResult(result);

            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return resultAction;
    }

}
