package com.tradingbot.binancebot.strategy;

import com.tradingbot.binancebot.enums.CalcType;
import com.tradingbot.binancebot.enums.CrossType;
import com.tradingbot.binancebot.enums.order.OrderSide;
import com.tradingbot.binancebot.orders.CreateNewOrder;
import com.tradingbot.binancebot.orders.old.MarketOrder;
import com.tradingbot.binancebot.orders.OrderService;
import com.tradingbot.binancebot.simple_indicators.EMA;
import com.tradingbot.binancebot.util.Services;


public class CrossEmaStrategy {
    //strategy settings
    private EMA ema9 = new EMA(9, CalcType.OPEN_PRICE);
    private EMA ema21 = new EMA(21,CalcType.OPEN_PRICE);
    //strategy settings

    Services services = new Services();
    private CrossType cross;
    private CreateNewOrder createNewOrder;
    private OrderService orderService = new OrderService();
    private MarketOrder marketOrder;


    public CrossEmaStrategy() {
    }

    public CrossType getCross() {
        return cross;
    }

    public void setCross(CrossType cross) {
        this.cross = cross;
    }

    public void run(String event){
        ema9.perform(event);
        ema21.perform(event);
        if(ema9.isValid()){
            if(isCross()){
                if(getCross() == CrossType.CROSS_UP){
                    orderService.closeAllOrders("BTCUSDT");
                    marketOrder = new MarketOrder(OrderSide.BUY);
                    marketOrder.openOrder();
                }else {
                    orderService.closeAllOrders("BTCUSDT");
                    marketOrder = new MarketOrder(OrderSide.SELL);
                    marketOrder.openOrder();
                }
            }
        }
    }

    private boolean isCross(){
        if((long) ema9.getEmaValuesList().size() < 2 || (long) ema21.getEmaValuesList().size() < 2 ){
            return false;
        }else{
            double valueEma9 = ema9.getEmaValuesList().get(ema9.getEmaValuesList().size()-1);
            double valueEma21 = ema21.getEmaValuesList().get(ema9.getEmaValuesList().size()-1);
            double previousValueEma9 = ema9.getEmaValuesList().get(ema21.getEmaValuesList().size()-2);
            double previousValueEma21 = ema21.getEmaValuesList().get(ema21.getEmaValuesList().size()-2);
            if(previousValueEma9 < previousValueEma21 && valueEma9 > valueEma21 ){
                setCross(CrossType.CROSS_UP);
                return true;
            }else if(previousValueEma9 > previousValueEma21 && valueEma9 < valueEma21){
                setCross(CrossType.CROSS_DOWN);
                return true;
            }
        }
        return false;
    }
}
