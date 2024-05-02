package com.tradingbot.binancebot.ui;

import com.tradingbot.binancebot.TradingBot;
import com.tradingbot.binancebot.account_information.ShowInfoAccount;
import com.tradingbot.binancebot.util.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    public static final String OPTION_SEPARATOR = "-----------------------------";
    private enum Option{
        EXIT("0. Exit"),
        START_BOT("1. Start bot"),
        TEST_CONNECTIVITY("2. Test database connectivity"),
        ACCOUNT_INFORMATION("3. Account information"),
        CHANGE_LEVERAGE("4. Change leverage"),
        POSITIONS_INFORMATION("5. Info positions"),
        ALL_ORDERS("6. Show all orders"),
        ALL_OPEN_ORDERS("7. Show current all open orders"),
        QUERY_ORDER("8. Query order"),
        QUERY_CURRENT_OPEN_ORDER("9. Query current open order"),
        EXCHANGE_INFO("10. Exchange info"),
        OPEN_LIMIT_ORDER_TEST("001. OPEN ORDER TEST"),
        CLOSE_ORDER_TEST("002. CLOSE ORDER TEST"),
        TEST_CANDLE("003. CANDLE TEST"),
        TEST_LISTEN_USER_STREAM("004. LISTEN USER STREAM"),
        CLOSE_ALL_ORDERS("005. LISTEN USER STREAM");



        private final String label;

        Option(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private static final Map<String, Option> OPTION_MAP = new HashMap<>();

    static {
        OPTION_MAP.put("0", Option.EXIT);
        OPTION_MAP.put("1", Option.START_BOT);
        OPTION_MAP.put("2", Option.TEST_CONNECTIVITY);
        OPTION_MAP.put("3", Option.ACCOUNT_INFORMATION);
        OPTION_MAP.put("4", Option.CHANGE_LEVERAGE);
        OPTION_MAP.put("5", Option.POSITIONS_INFORMATION);
        OPTION_MAP.put("6", Option.ALL_ORDERS);
        OPTION_MAP.put("7", Option.ALL_OPEN_ORDERS);
        OPTION_MAP.put("8", Option.QUERY_ORDER);
        OPTION_MAP.put("9", Option.QUERY_CURRENT_OPEN_ORDER);
        OPTION_MAP.put("10", Option.EXCHANGE_INFO);
        OPTION_MAP.put("001", Option.OPEN_LIMIT_ORDER_TEST);
        OPTION_MAP.put("002", Option.CLOSE_ORDER_TEST);
        OPTION_MAP.put("003", Option.TEST_CANDLE);
        OPTION_MAP.put("004", Option.TEST_LISTEN_USER_STREAM);
        OPTION_MAP.put("005", Option.CLOSE_ALL_ORDERS);
    }

    public static void showMenu(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            showOptions();
            System.out.print("Type your option and hit enter! : ");
            String answer = scanner.nextLine();
            Option option = OPTION_MAP.get(answer);
            if(option != null){
                runOption(option);
            }else {
                System.out.println("Invalid choice.");
            }
            System.out.println(OPTION_SEPARATOR);
        }
    }

    private static void showOptions(){
        for (Option option : Option.values()){
            System.out.println(option.getLabel());
        }
        System.out.println(OPTION_SEPARATOR);
    }

    private static void runOption(Option option) {
        try {
            switch (option) {
                case EXIT -> {
                    System.out.println("Exiting...Menu");
                    System.exit(0);
                }
                case START_BOT ->{
                 System.out.println("Starting bot...");
                 new TradingBot().startBot();
                }
                case TEST_CONNECTIVITY -> System.out.println("Not implement");
                case ACCOUNT_INFORMATION -> System.out.println(new ShowInfoAccount().getAccountInformation());
                case CHANGE_LEVERAGE -> System.out.println(new ShowInfoAccount().changeLeverage());
                case POSITIONS_INFORMATION -> System.out.println(new ShowInfoAccount().showInfoPositions());
                case ALL_ORDERS -> System.out.println(new ShowInfoAccount().showAllOrders());
                case ALL_OPEN_ORDERS -> System.out.println(new ShowInfoAccount().showCurrentAllOpenOrders());
                case QUERY_ORDER -> {
                    String status = new ShowInfoAccount().showQueryOrder();
                    System.out.println("event: " + status);
                    System.out.println("Param status: " + Services.extractParamFromJSON("status",status));
                    //System.out.println(new ShowInfoAccount().showQueryOrder());
                }
                case QUERY_CURRENT_OPEN_ORDER -> System.out.println(new ShowInfoAccount().showQueryCurrentOpenOrder());
                case EXCHANGE_INFO -> System.out.println(new ShowInfoAccount().showExchangeInfo());
                case OPEN_LIMIT_ORDER_TEST -> System.out.println(new ShowInfoAccount().openOnePosition());
                case CLOSE_ORDER_TEST -> System.out.println(new ShowInfoAccount().closeOnePosition());
                case TEST_CANDLE -> new ShowInfoAccount().showCandlestick();
                case TEST_LISTEN_USER_STREAM -> new ShowInfoAccount().showListenUserStream();
                case CLOSE_ALL_ORDERS -> new ShowInfoAccount().closeAllOrders2();

                default -> System.out.println("Invalid choice");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
