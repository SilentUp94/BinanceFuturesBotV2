package com.tradingbot.binancebot.service;

import com.tradingbot.binancebot.repository.UserStreamEvent;
import com.tradingbot.binancebot.repository.UserStreamRepo;
import com.tradingbot.binancebot.util.Services;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

public class UserStreamService {

    private final UserStreamRepo userStreamRepo = UserStreamRepo.getInstance();

    public UserStreamService() {
    }

    public UserStreamEvent save(String event) {
        UserStreamEvent userStreamEvent= null;
        String eventType = Services.extractParamFromJSON("e",event).toString();
        System.out.println("event : " + event);
        if(eventType.equals("ORDER_TRADE_UPDATE")){
            JSONObject firstLayer = new JSONObject(event);
            JSONObject secondLayer = firstLayer.getJSONObject("o");
            String status = secondLayer.getString("X");
            if(status.equals("FILLED")){
                Long orderId = secondLayer.getLong("i");
                userStreamEvent = new UserStreamEvent(eventType, orderId, status);
                userStreamRepo.getUserStreamEvents().add(userStreamEvent);
                }
        }
        return userStreamEvent;
    }

    public UserStreamEvent findByOrderId(Long orderId){
        List<UserStreamEvent> userStreamEventList = userStreamRepo.getUserStreamEvents();
        Optional<UserStreamEvent> userStreamEvent = userStreamEventList.stream().filter(id->orderId.equals(id.getOrderId())).findFirst();
        return userStreamEvent.orElse(null);
    }

    public UserStreamEvent findByEventId(Long eventId){
        List<UserStreamEvent> userStreamEventList = userStreamRepo.getUserStreamEvents();
        Optional<UserStreamEvent> userStreamEvent = userStreamEventList.stream().filter(id->eventId.equals(id.getEventId())).findFirst();
        return userStreamEvent.orElse(null);
    }


    public UserStreamEvent remove(UserStreamEvent userStreamEvent) {
        userStreamRepo.getUserStreamEvents().remove(userStreamEvent);
        return userStreamEvent;
    }

    public List<UserStreamEvent> getUserStreamEvents(){
        return userStreamRepo.getUserStreamEvents();
    }

    public void clearList() {
            userStreamRepo.getUserStreamEvents().clear();
    }
}
