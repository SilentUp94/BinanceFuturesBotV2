package com.tradingbot.binancebot.repository;

import com.tradingbot.binancebot.orders.CreateNewOrder;
import com.tradingbot.binancebot.orders.FullOrder;
import com.tradingbot.binancebot.orders.ProcessedOrder;

import java.util.ArrayList;
import java.util.List;

public class FullOrderRepo {

    private final List<FullOrder> fullOrderList = new ArrayList<>();
    private static FullOrderRepo fullOrderRepo;
    private FullOrderRepo() {
    }
    public List<FullOrder> getFullOrderList() {
        return fullOrderList;
    }

    public static FullOrderRepo getInstance(){
        if(fullOrderRepo == null){
            fullOrderRepo = new FullOrderRepo();
        }
        return fullOrderRepo;
    }
}
