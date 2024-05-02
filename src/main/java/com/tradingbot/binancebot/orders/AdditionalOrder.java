package com.tradingbot.binancebot.orders;

import com.tradingbot.binancebot.enums.order.OrderSide;
import com.tradingbot.binancebot.enums.order.OrderType;
import com.tradingbot.binancebot.util.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta4j.core.Bar;

public class AdditionalOrder {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalOrder.class);

    public AdditionalOrder() {
    }

    public CreateNewOrder buildOrder(CreateNewOrder primaryOrder, OrderType type,Double tradePercentage){
        System.out.println("===============================================================");
        CreateNewOrder additionalOrder = new CreateNewOrder();
        logger.info("order type: " + type );
        OrderSide side = primaryOrder.getSide().equals(OrderSide.BUY) ? OrderSide.SELL : OrderSide.BUY;
        int param = side.equals(OrderSide.SELL) && type.equals(OrderType.TAKE_PROFIT) ||
                side.equals(OrderSide.BUY) && type.equals(OrderType.STOP) ? 1 : -1 ;
        //Double activatePrice = Math.round(primaryOrder.getPrice() *(100 + (param *  tradePercentage))/100.0 * 100.0)/100.0;

//        Double executePrice =  Math.round(primaryOrder.getPrice() *(100 + (param *  tradePercentage))/100.0 * 100.0)/100.0;
//        Double finalExecutePrice = Math.round(executePrice / 0.10) * 0.10;

        Double finalExecutePrice = Services.rightFormat(primaryOrder.getPrice() *(100 + (param *  tradePercentage))/100, "#####.#");
        logger.info("Final price : " + finalExecutePrice);

        additionalOrder.setType(type);
        additionalOrder.setSide(side);
        additionalOrder.setPrice(finalExecutePrice);
        additionalOrder.setStopPrice(finalExecutePrice);
        additionalOrder.setReduceOnly(true);
        System.out.println("===============================================================");

        return additionalOrder;
    }

//    public CreateNewOrder buildOrder(CreateNewOrder primaryOrder, OrderType type, Bar candle){
//        System.out.println("===============================================================");
//        CreateNewOrder additionalOrder = new CreateNewOrder();
//        logger.info("order type: " + type );
//        OrderSide side = primaryOrder.getSide().equals(OrderSide.BUY) ? OrderSide.SELL : OrderSide.BUY;
//        int param = side.equals(OrderSide.SELL) && type.equals(OrderType.TAKE_PROFIT) ||
//                side.equals(OrderSide.BUY) && type.equals(OrderType.STOP) ? 1 : -1 ;
//        //Double activatePrice = Math.round(primaryOrder.getPrice() *(100 + (param *  tradePercentage))/100.0 * 100.0)/100.0;
//
////        Double executePrice =  Math.round(primaryOrder.getPrice() *(100 + (param *  tradePercentage))/100.0 * 100.0)/100.0;
////        Double finalExecutePrice = Math.round(executePrice / 0.10) * 0.10;
//
//        Double finalExecutePrice = Services.rightFormat(primaryOrder.getPrice() *(100 + (param *  tradePercentage))/100, "#####.#");
//        logger.info("Final price : " + finalExecutePrice);
//
//        additionalOrder.setType(type);
//        additionalOrder.setSide(side);
//        additionalOrder.setPrice(finalExecutePrice);
//        additionalOrder.setStopPrice(finalExecutePrice);
//        additionalOrder.setReduceOnly(true);
//        System.out.println("===============================================================");
//
//        return additionalOrder;
    //}

}
