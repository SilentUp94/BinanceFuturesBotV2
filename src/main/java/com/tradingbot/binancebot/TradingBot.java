package com.tradingbot.binancebot;

import com.binance.api.client.domain.market.CandlestickInterval;
import com.tradingbot.binancebot.api.ConnectionService;
import com.tradingbot.binancebot.api.IClient;
import com.tradingbot.binancebot.enums.LocalFileName;
import com.tradingbot.binancebot.repository.old.OrderRepository;
import com.tradingbot.binancebot.service.UserStreamService;
import com.tradingbot.binancebot.strategy.grannyStrategy.GrannyStrategy;
import com.tradingbot.binancebot.util.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class TradingBot implements IClient {
    private final GrannyStrategy grannyStrategy = new GrannyStrategy();
    private final ConnectionService  connectionService = new ConnectionService();
    private final UserStreamService userStreamService = new UserStreamService();

    public void clearFiles(){
        Services services = new Services();
        services.clearFile(LocalFileName.CANDLE_SERIES);
        services.clearFile(LocalFileName.TRADES);
    }
    public void startBot(){

        Thread thread1 = new Thread(() -> {
            try {
            Thread.sleep(1000);
            System.out.println("Thread 1 - started...");
            websocketClient.klineStream("BTCUSDT", CandlestickInterval.FIFTEEN_MINUTES.getIntervalId(), this::handleCandleEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectionService.keepConnectionAlive();
        });
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(3000); // așteaptă 3 secundă
                System.out.println("Thread 2 - started...");
                websocketClient.listenUserStream(ConnectionService.getListenKey(), this::handleEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }


    private void handleEvent(String event){
        userStreamService.save(event);
    }

    private void handleCandleEvent(String event) {
        grannyStrategy.runStrategy(event);
        //Services.writeToFile("New candle! Time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),event,LocalFileName.CANDLE_SERIES);
    }


}
