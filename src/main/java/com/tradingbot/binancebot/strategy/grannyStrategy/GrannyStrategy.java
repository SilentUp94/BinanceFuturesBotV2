package com.tradingbot.binancebot.strategy.grannyStrategy;

import com.binance.api.client.domain.account.Order;
import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.tradingbot.binancebot.repository.UserStreamEvent;
import com.tradingbot.binancebot.enums.CrossType;
import com.tradingbot.binancebot.enums.LocalFileName;
import com.tradingbot.binancebot.enums.Position;
import com.tradingbot.binancebot.enums.order.OrderSide;
import com.tradingbot.binancebot.enums.order.OrderType;
import com.tradingbot.binancebot.orders.*;
import com.tradingbot.binancebot.repository.FullOrderRepo;
import com.tradingbot.binancebot.service.FullOrderRepoServices;
import com.tradingbot.binancebot.service.UserStreamService;
import com.tradingbot.binancebot.util.Services;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GrannyStrategy {
    //strategy settings
    private final GrannyStrategySettings strategySettings = new GrannyStrategySettings();
    private final Double tickSize = OrderSettings.tickSize;
    //strategy settings

    private GrannyStrategyServices grannyStrategyServices = new GrannyStrategyServices();
    private Position candlePosition = null;
    private Position trend = null;

    // testing

    private static final Logger logger = LoggerFactory.getLogger(GrannyStrategy.class);
    private final FullOrderRepo fullOrderRepo = FullOrderRepo.getInstance();
    private final FullOrderRepoServices fullOrderRepoServices = new FullOrderRepoServices();
    private final UserStreamService userStreamService = new UserStreamService();
    private final Services services = new Services();

    private final List<Bar> pastCandles = new ArrayList<>();

    public GrannyStrategy() {
    }

    public void runStrategy(String event) {
        strategySettings.getEmaLow().perform(event);
        strategySettings.getEmaHigh().perform(event);
        JSONObject json = new JSONObject(event);
        JSONObject kline = json.getJSONObject("k");
        if (kline.getBoolean("x")) {
            processStrategyParameters(event);
        }

        if (!fullOrderRepo.getFullOrderList().isEmpty()) { // daca este limit order plasat
            FullOrder checkOrder = fullOrderRepo.getFullOrderList().get(fullOrderRepo.getFullOrderList().size() - 1);
            if (checkOrder.getPrimaryPlaced() && !checkOrder.getPrimaryFilled()) {
                checkIfPrimaryExecuted(checkOrder);
            }
            if (checkOrder.getTPPlaced() & checkOrder.getSLPlaced()) {
                checkIfAdditionalExecuted(checkOrder);
            }
            if (!fullOrderRepo.getFullOrderList().isEmpty()) {
                if (!fullOrderRepo.getFullOrderList().get(fullOrderRepo.getFullOrderList().size() - 1).getPrimaryFilled()
                        && strategySettings.getEmaLow().getNewTimeFrameEvent()) {
                    System.out.println(
                            fullOrderRepo.getFullOrderList().get(fullOrderRepo.getFullOrderList().size() - 1).toStringV2()
                    );
                    fullOrderRepoServices.clearWaitingOrders();
                    placeInitialPosition();
                }
            }

        } else {
            if (strategySettings.getEmaLow().getNewTimeFrameEvent()) {
                placeInitialPosition();
            }
        }
    }

    private void placeInitialPosition() {
        if (candlePosition == null || trend == null) {
            System.out.println("=============Invalid to place initial position===============");
            System.out.println(trend);
            System.out.println(candlePosition);
            System.out.println(LocalDateTime.now());
            return;
        }
        if (candlePosition == Position.ABOVE && trend == Position.ABOVE) {
            System.out.println(trend);
            System.out.println(candlePosition);
            fullOrderRepoServices.clearWaitingOrders(); // doar daca nu exista ordin activ
            placeNewTrade(OrderSide.BUY, OrderType.LIMIT);
        } else if (candlePosition == Position.BELOW && trend == Position.BELOW) {
            System.out.println(trend);
            System.out.println(candlePosition);
            fullOrderRepoServices.clearWaitingOrders(); // doar daca nu exista ordin activ
            placeNewTrade(OrderSide.SELL, OrderType.LIMIT);
        }
    }

    private void placeNewTrade(OrderSide side, OrderType type) {
        Double delta = grannyStrategyServices.TPGetDelta(pastCandles.get(pastCandles.size() - 1),
                strategySettings.getEmaLow().getNextEmaValue());
        if (delta > 2 * tickSize) {
            FullOrder fullOrder = new FullOrder();
            CreateNewOrder primaryOrder = new CreateNewOrder(side, type);
            primaryOrder.setPrice(Services.rightFormat(strategySettings.getEmaLow().getNextEmaValue(), "####.#"));
            String primaryOrderResult = null;
            try {
                System.out.println("try Opening new primary order");
                primaryOrderResult = primaryOrder.openOrder();
                System.out.println("success Opening new primary order");
            } catch (BinanceConnectorException | BinanceClientException bce) {
                System.out.println("FRom primary");
                return;
            }
            fullOrder.setPrimaryOrder(primaryOrder);
            fullOrder.setPrimaryOrderId((Long) Services.extractParamFromJSON("orderId", primaryOrderResult));
            fullOrder.setPrimaryPlaced(true);

            CreateNewOrder takeProfitOrder = new GrannyStrategyServices().createTPOrder(primaryOrder, delta);
            fullOrder.setTakeProfitOrder(takeProfitOrder);

            CreateNewOrder stopLossOrder = new GrannyStrategyServices().createSLOrder(primaryOrder, delta);
            fullOrder.setStopLossOrder(stopLossOrder);

            System.out.println(
                    fullOrder.toStringV2()
            );
            fullOrderRepoServices.save(fullOrder);
            Services.writeToFile("Open new order:", primaryOrderResult, LocalFileName.TRADES);

            //check
            System.out.println("Full order details from placeNewOrder " + "\n" + fullOrder);
            //check
        } else {
            System.out.println("not enough!");
        }
    }

    private void checkIfPrimaryExecuted(FullOrder fullOrder) {
        if (trackOrder(fullOrder.getPrimaryOrderId())) {
            fullOrder.setPrimaryFilled(true);
            placeAdditionalOrders(fullOrder);
            return;
        }
        fullOrder.setPrimaryFilled(false);
    }

    private Boolean trackOrder(Long orderId) {
        List<UserStreamEvent> userStreamEvents = userStreamService.getUserStreamEvents();
        for (UserStreamEvent e : userStreamEvents) {
            if (orderId.equals(e.getOrderId())) {
                if (!"FILLED".equals(e.getStatus())) {
                    userStreamService.remove(userStreamService.findByEventId(e.getEventId()));
                } else {
                    userStreamService.remove(userStreamService.findByEventId(e.getEventId()));
                    return true;
                }
            }
        }
        return false;
    }

    private void checkIfAdditionalExecuted(FullOrder fullOrder) {
        if (trackOrder(fullOrder.getTakeProfitOrderId())) {
            fullOrder.setTakeProfitOrder(null);
            fullOrder.setTPisFilled(true);
            System.out.println(
                    fullOrder.toStringV2()
            );
            fullOrderRepoServices.clearWaitingOrders();
            return;
        }

        if (trackOrder(fullOrder.getStopLossOrderId())) {
            fullOrder.setStopLossOrder(null);
            fullOrder.setSLisFilled(true);
            System.out.println(
                    fullOrder.toStringV2()
            );
            fullOrderRepoServices.clearWaitingOrders();
        }
    }

    private void placeAdditionalOrders(FullOrder fullOrder) {
        String takeProfitResult = null;
        try {
            System.out.println("****************Opening new TP");
            takeProfitResult = fullOrder.getTakeProfitOrder().openOrder();
        } catch (BinanceConnectorException bce) {
            System.out.println("****************BinanceConnectorException");
            return;
        } catch (BinanceClientException bce) {
            System.out.println("****************BinanceClientException");
            System.out.println("**************** " + fullOrder.getPrimaryOrderId());
            //new OrderService().closeOrderById(fullOrder.getPrimaryOrderId(), OrderSettings.SYMBOL);
            System.out.println(grannyStrategyServices
                    .closeActiveOrder(fullOrder.getPrimaryOrder()
                            .getSide(), fullOrder
                            .getPrimaryOrder().getSymbol()));

            fullOrderRepoServices.clearWaitingOrders();
            //fullOrderRepo.getFullOrderList().clear();
            return;
        }

        System.out.println("****************Opening new TP************* SUCCESS");
        fullOrder.setTPPlaced(true);
        fullOrder.setTakeProfitOrderId((Long) Services.extractParamFromJSON("orderId", takeProfitResult));
        //System.out.println("Test takeProfitOrderId: " + fullOrder.getTakeProfitOrderId());
        Services.writeToFile("Take Profit:", takeProfitResult, LocalFileName.TRADES);
//        }
//        if (!fullOrder.getSLPlaced()) {
        String stopLossResult = null;
        try {
            System.out.println("****************Opening new SL");
            stopLossResult = fullOrder.getStopLossOrder().openOrder();
        } catch (BinanceConnectorException bce) {
            System.out.println("****************BinanceConnectorException");
            return;
        } catch (BinanceClientException bce) {
            System.out.println("****************BinanceClientException");
            System.out.println("**************** " + fullOrder.getPrimaryOrderId() + "Symbol: " + fullOrder.getPrimaryOrder().getSymbol());
            System.out.println(grannyStrategyServices
                    .closeActiveOrder(fullOrder.getPrimaryOrder()
                            .getSide(), fullOrder
                            .getPrimaryOrder().getSymbol()));
            fullOrderRepoServices.clearWaitingOrders();
            return;
        }
        System.out.println("****************Opening new SL************* SUCCESS");
        fullOrder.setSLPlaced(true);
        fullOrder.setStopLossOrderId((Long) Services.extractParamFromJSON("orderId", stopLossResult));
        Services.writeToFile("Stop Loss:", stopLossResult, LocalFileName.TRADES);
    }

    private void processStrategyParameters(String event) {
        Bar candle = services.createBar(event);
        pastCandles.add(candle);
        System.out.println("Ema: " + strategySettings.getEmaLow().getEmaValue());
        this.candlePosition = grannyStrategyServices.calculateCandlePosition(candle, strategySettings);
        this.trend = grannyStrategyServices.calculateTrend(strategySettings);

    }

}
