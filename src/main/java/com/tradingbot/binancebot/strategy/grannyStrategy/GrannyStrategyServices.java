package com.tradingbot.binancebot.strategy.grannyStrategy;

import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.Order;
import com.tradingbot.binancebot.enums.Position;
import com.tradingbot.binancebot.enums.order.OrderSide;
import com.tradingbot.binancebot.enums.order.OrderType;
import com.tradingbot.binancebot.orders.CreateNewOrder;
import com.tradingbot.binancebot.orders.FullOrder;
import com.tradingbot.binancebot.orders.OrderSettings;
import com.tradingbot.binancebot.util.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;

import java.util.List;

public class GrannyStrategyServices {
    private static final Logger logger = LoggerFactory.getLogger(GrannyStrategyServices.class);
    public GrannyStrategyServices() {
    }

    public Double TPGetDelta(Bar candle, Double primaryOrderPrice){
        double deltaA = Math.abs(primaryOrderPrice - candle.getLowPrice().doubleValue());
        double deltaB = Math.abs(primaryOrderPrice - candle.getHighPrice().doubleValue());
//        System.out.println("DELTA: " + "\n" +
//                "Price: " + primaryOrderPrice + "\n" +
//                "Bar high price: " + candle.getHighPrice().doubleValue() + "\n" +
//                "Bar low price: " + candle.getLowPrice() + "\n" +
//                "Delta A low: " + deltaA+"\n" +
//                "Delta B high: " + deltaB);
        return Math.max(deltaA,deltaB);
//        Double deltaA = Math.abs(primaryOrderPrice - candle.getOpenPrice().doubleValue());
//        Double deltaB = Math.abs(primaryOrderPrice - candle.getClosePrice().doubleValue());
//        Double price =  Math.max(deltaA,deltaB);
//        return price;
    }
    public CreateNewOrder createTPOrder(CreateNewOrder primaryOrder, Double delta){
        //System.out.println("===============================================================");
        CreateNewOrder TPOrder = new CreateNewOrder();
        OrderSide side = primaryOrder.getSide().equals(OrderSide.BUY) ? OrderSide.SELL : OrderSide.BUY;
        int param = primaryOrder.getSide().equals(OrderSide.BUY) ? 1 : -1 ;
        Double executePrice = primaryOrder.getPrice() + (param)*delta;
        Double tickSize = OrderSettings.tickSize;
        //System.out.println("TICK SIZE TP = " + tickSize);
        String formatted = String.format("%.2f",Math.round(executePrice / tickSize) * tickSize);
        Double finalExecutePrice = Double.parseDouble(formatted);
        //logger.info("Final price TP : " + finalExecutePrice);
        TPOrder.setSide(side);
        TPOrder.setType(OrderType.TAKE_PROFIT);
        TPOrder.setPrice(finalExecutePrice);
        TPOrder.setStopPrice(finalExecutePrice);
        TPOrder.setReduceOnly(true);
        //System.out.println("===============================================================");

        return TPOrder;
    }
    public CreateNewOrder createSLOrder(CreateNewOrder primaryOrder, Double delta){
        //System.out.println("===============================================================");
        CreateNewOrder SLOrder = new CreateNewOrder();
        OrderSide side = primaryOrder.getSide().equals(OrderSide.BUY) ? OrderSide.SELL : OrderSide.BUY;
        int param = primaryOrder.getSide().equals(OrderSide.BUY) ? -1 : 1 ;
        double deltaSL = delta/2.0;
        Double executePrice = primaryOrder.getPrice() + (param)*(deltaSL);
        Double tickSize = OrderSettings.tickSize;
        //System.out.println("TICK SIZE SL = " + tickSize);
        String formatted = String.format("%.2f",Math.round(executePrice / tickSize) * tickSize);
        Double finalExecutePrice = Double.parseDouble(formatted);
        //logger.info("Final price SL : " + finalExecutePrice);
        SLOrder.setSide(side);
        SLOrder.setType(OrderType.STOP);
        SLOrder.setPrice(finalExecutePrice);
        SLOrder.setStopPrice(finalExecutePrice);
        SLOrder.setReduceOnly(true);
        //System.out.println("===============================================================");
        return SLOrder;
    }
    public Position calculateCandlePosition(Bar candle, GrannyStrategySettings strategySettings) {
        if (candle.getLowPrice().doubleValue() > strategySettings.getEmaLow().getEmaValue()
                && candle.getLowPrice().doubleValue() > strategySettings.getEmaHigh().getEmaValue()) {
            return Position.ABOVE;
        } else if (candle.getHighPrice().doubleValue() < strategySettings.getEmaLow().getEmaValue()
                && candle.getHighPrice().doubleValue() < strategySettings.getEmaHigh().getEmaValue()) {
            return  Position.BELOW;
        }
        return null;
    }

    public Position calculateTrend(GrannyStrategySettings strategySettings) {
        if (strategySettings.getEmaLow().getEmaValue()> strategySettings.getEmaHigh().getEmaValue()) {
            return Position.ABOVE;
        } else if (strategySettings.getEmaLow().getEmaValue() < strategySettings.getEmaHigh().getEmaValue()) {
            return Position.BELOW;
        }
        return null;
    }

    public String closeActiveOrder(OrderSide side, String symbol){
        OrderSide sideOrder = "BUY".equals(side.text) ? OrderSide.SELL : OrderSide.BUY;
        CreateNewOrder closingOrder = new CreateNewOrder();
        closingOrder.setType(OrderType.MARKET);
        closingOrder.setSide(sideOrder);
        closingOrder.setSymbol(symbol);
        closingOrder.setQuantity(0.001);
//        closingOrder.setRecvWindow(5000L);
        closingOrder.setReduceOnly(true);
        closingOrder.setTimeInForce(null);
        return closingOrder.openOrder();
    }

    public double delta(GrannyStrategySettings strategySettings) {
        return Math.abs(strategySettings.getEmaLow().getEmaValue() - strategySettings.getEmaHigh().getEmaValue());
    }



    public void showActualSituation(List<Bar> pastCandles,GrannyStrategySettings strategySettings, Position trend){
        System.out.println("ultima candela: " + pastCandles.get(pastCandles.size()-1));
        System.out.println("last Ema9: " + strategySettings.getEmaLow().getEmaValuesList().get(strategySettings.getEmaLow().getEmaValuesList().size()-1));
        System.out.println("last Ema21: " + strategySettings.getEmaHigh().getEmaValuesList().get(strategySettings.getEmaHigh().getEmaValuesList().size()-1));
        System.out.println("trend " + trend);
    }
}
