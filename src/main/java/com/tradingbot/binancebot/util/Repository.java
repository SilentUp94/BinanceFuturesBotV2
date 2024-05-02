package com.tradingbot.binancebot.util;

import com.tradingbot.binancebot.orders.EntityOrder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    private List<EntityOrder> limitOrders = new ArrayList<>();
    private List<EntityOrder> marketOrders = new ArrayList<>();
    private List<EntityOrder> activeOrders = new ArrayList<>();
    private List<EntityOrder> closedOrders = new ArrayList<>();

    public Repository() {
    }
    public static Object extractParamFromJSON(String param, String jsonFile){
        JSONObject jsonObject = new JSONObject(jsonFile);
        return jsonObject.get(param);
    }

//    public static void addToList(List<EntityOrder> list, String jsonFile){
//        JSONObject jsonObject = new JSONObject(jsonFile);
//        Map<String, String> tempMap = new HashMap<>();
//        for(Map.Entry<String,Object> entry : jsonObject.toMap().entrySet()){
//            tempMap.put(entry.getKey(),(String)entry.getValue());
//        }
//        list.add(tempMap);
//    }




}
