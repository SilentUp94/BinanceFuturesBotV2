package com.tradingbot.binancebot.repository;

import com.tradingbot.binancebot.orders.FullOrder;

import java.util.ArrayList;
import java.util.List;

public class UserStreamRepo {
    private final List<UserStreamEvent> userStreamEvents = new ArrayList<>();
    private static UserStreamRepo userStreamRepo;
    private UserStreamRepo() {
    }
    public List<UserStreamEvent> getUserStreamEvents() {
        return userStreamEvents;
    }

    public static UserStreamRepo getInstance(){
        if(userStreamRepo == null){
            userStreamRepo = new UserStreamRepo();
        }
        return userStreamRepo;
    }
}
