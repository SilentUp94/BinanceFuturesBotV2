package com.tradingbot.binancebot.account_information;


import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.tradingbot.binancebot.api.IClient;
import com.tradingbot.binancebot.enums.order.OrderSide;
import com.tradingbot.binancebot.enums.order.OrderType;
import com.tradingbot.binancebot.orders.CreateNewOrder;
import com.tradingbot.binancebot.orders.OrderService;
import com.tradingbot.binancebot.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;


public class ShowInfoAccount implements IClient {

    private static final Logger logger = LoggerFactory.getLogger(ShowInfoAccount.class);
    private Scanner scanner = new Scanner(System.in);

    public String getAccountInformation() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        String result = null;
        try {
            result = futuresClient.account().accountInformation(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public String changeLeverage() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        System.out.print("Enter symbol: ");
        String symbol = scanner.nextLine();

        System.out.print("Enter leverage: ");
        Integer leverage = Integer.valueOf(scanner.nextLine());

        parameters.put("symbol", "BTCUSDT");
        parameters.put("leverage", leverage);

        String result = null;

        try {
            result = futuresClient.account().changeInitialLeverage(parameters);
            logger.info(result);
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
        }
        return result;
    }

    public String showQueryOrder(){
        String symbol = Helper.showInputField("Enter symbol: ");
        Long orderId = Long.valueOf(Helper.showInputField("Enter orderId: "));
        return new InfoOrder().getQueryOrder(symbol,orderId);
    }

    public String showQueryCurrentOpenOrder(){
        String symbol = Helper.showInputField("Enter symbol: ");
        Long orderId = Long.valueOf(Helper.showInputField("Enter orderId: "));
        return new InfoOrder().getQueryCurrentOpenOrder(symbol,orderId);
    }


    public String showCurrentAllOpenOrders(){
        String symbol = Helper.showInputField("Enter symbol: ");
        //Long orderId = Long.valueOf(Helper.showInputField("Enter orderId"));
        return new InfoOrder().getCurrentAllOpenOrders(symbol);
    }

    public String showAllOrders(){
        String symbol = Helper.showInputField("Enter symbol: ");
        return new InfoOrder().getAllOrders(symbol);
    }

    public String showInfoPositions() {
        String symbol = Helper.showInputField("Enter symbol: ");
        Long orderId = Long.valueOf( Helper.showInputField("Enter orderId: "));
        return new InfoOrder().getInfoPositions(symbol,orderId);
    }

    public void closeAllOrders2(){
        new OrderService().closeAllOrders("BTCUSDT");
    }

    public String openOnePosition(){


        CreateNewOrder newOrder = new CreateNewOrder();

        String side =  Helper.showInputField("Enter side: ");
        Double quantity = Double.valueOf(Helper.showInputField("Enter Quantity: "));
        OrderSide orderSide =  side.equals("BUY") ? OrderSide.BUY : OrderSide.SELL;

        Double entryPrice = Double.valueOf( Helper.showInputField("Enter price: "));

        newOrder.setType(OrderType.LIMIT);
        newOrder.setSide(orderSide);
        newOrder.setPrice(entryPrice);
        System.out.println("Execute new order!");
        newOrder.setQuantity(quantity);
        String newOrderResult =  newOrder.openOrder();


//        CreateNewOrder takeProfitOrder = new CreateNewOrder();
//        Double takeProfitPrice = Math.round(entryPrice * 1.1 * 100.0)/100.0;
//        Double executePriceTP = Math.round(entryPrice * 1.1 * 100.0)/100.0;
//        OrderSide takeProfitSide = newOrder.getSide().equals(OrderSide.BUY) ? OrderSide.SELL : OrderSide.BUY;
//        System.out.println(" NEW ORDER SIDE 222 ============ " +newOrder.getSide().getText());
//        takeProfitOrder.setType(OrderType.TAKE_PROFIT);
//        takeProfitOrder.setSide(takeProfitSide);
//        takeProfitOrder.setPrice(executePriceTP);
//        takeProfitOrder.setStopPrice(takeProfitPrice);
//        //takeProfitOrder.setReduceOnly(true);
//        System.out.println("Execute take profit!");
//        String takeProfitResult = takeProfitOrder.openOrder();
//
//
//        CreateNewOrder stopLossOrder = new CreateNewOrder();
//        System.out.println(" NEW ORDER SIDE ============ " +newOrder.getSide().getText());
//        Double stopLossPrice = Math.round(entryPrice * 0.90 * 100.0)/100.0;
//        Double executePriceSL = Math.round(entryPrice * 0.90 * 100.0)/100.0;
//        OrderSide stopLoss = newOrder.getSide().equals(OrderSide.BUY) ? OrderSide.SELL : OrderSide.BUY;
//        stopLossOrder.setType(OrderType.STOP_MARKET);
//        stopLossOrder.setPrice(null);
//        stopLossOrder.setStopPrice(stopLossPrice);
//        stopLossOrder.setSide(stopLoss);
//        //stopLossOrder.setReduceOnly(true);
//        System.out.println("Execute stop loss!");
//        String stopLossResult = stopLossOrder.openOrder();

        return "New order : " + newOrderResult + "\n";
//                + "Take Profit : " + takeProfitResult + "\n"
//                + "Stop Loss : " + stopLossResult + "\n";

    }

    public String showExchangeInfo() {
        return new InfoOrder().getExchangeInfo();
    }

    public String closeOnePosition(){
        OrderService orderService = new OrderService();
        Long orderId = Long.valueOf( Helper.showInputField("Enter orderId: "));
        return orderService.closeOrderById(orderId,"BTCUSDT");
    }

    public void showCandlestick(){
        Integer lookingBack = Integer.valueOf(Helper.showInputField("How many candles do you want to see? : "));
         new InfoOrder().candlestickList("BTCUSDT",CandlestickInterval.ONE_MINUTE,lookingBack);

    }

    public void showListenUserStream(){
        new InfoOrder().useListenUserStream();
    }







}
