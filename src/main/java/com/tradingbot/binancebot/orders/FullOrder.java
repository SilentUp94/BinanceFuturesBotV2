package com.tradingbot.binancebot.orders;

import com.tradingbot.binancebot.util.Services;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Optional;

public class FullOrder {
    private Boolean primaryFilled = false;
    private CreateNewOrder primaryOrder;
    private Long primaryOrderId;
    private Boolean isPrimaryPlaced = false;

    private CreateNewOrder takeProfitOrder;
    private Long takeProfitOrderId;
    private Boolean isTPPlaced = false;
    private Boolean TPisFilled = false;

    private CreateNewOrder stopLossOrder;
    private Long stopLossOrderId;
    private Boolean isSLPlaced = false;
    private Boolean SLisFilled = false;

    private Boolean isFull;// = primaryOrderId == null && takeProfitOrderId == null && stopLossOrderId == null ? true : false;
    public FullOrder() {
    }

    public Boolean getPrimaryFilled() {
        return primaryFilled;
    }

    public void setPrimaryFilled(Boolean primaryFilled) {
        this.primaryFilled = primaryFilled;
    }

    public Boolean getTPisFilled() {
        return TPisFilled;
    }

    public void setTPisFilled(Boolean TPisFilled) {
        this.TPisFilled = TPisFilled;
    }

    public Boolean getSLisFilled() {
        return SLisFilled;
    }

    public void setSLisFilled(Boolean SLisFilled) {
        this.SLisFilled = SLisFilled;
    }

    public CreateNewOrder getPrimaryOrder() {
        if(primaryFilled != null){
            return primaryOrder;
        }else {
            return null;
        }
    }

    public void setPrimaryOrder(CreateNewOrder primaryOrder) {
        this.primaryOrder = primaryOrder;
    }

    public Boolean getPrimaryPlaced() {
        return isPrimaryPlaced;
    }

    public void setPrimaryPlaced(Boolean primaryPlaced) {
        isPrimaryPlaced = primaryPlaced;
    }

    public CreateNewOrder getTakeProfitOrder() {
        if(takeProfitOrder != null){
            return takeProfitOrder;
        }else {
            return null;
        }
    }

    public void setTakeProfitOrder(CreateNewOrder takeProfitOrder) {
        this.takeProfitOrder = takeProfitOrder;
    }

    public Boolean getTPPlaced() {
        return isTPPlaced;
    }

    public void setTPPlaced(Boolean TPPlaced) {
        isTPPlaced = TPPlaced;
    }

    public CreateNewOrder getStopLossOrder() {
        if(stopLossOrder != null ){
            return stopLossOrder;
        }else {
            return null;
        }
    }

    public void setStopLossOrder(CreateNewOrder stopLossOrder) {
        this.stopLossOrder = stopLossOrder;
    }

    public Boolean getSLPlaced() {
        return isSLPlaced;
    }

    public void setSLPlaced(Boolean SLPlaced) {
        isSLPlaced = SLPlaced;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public Long getPrimaryOrderId() {
        return primaryOrderId;
    }

    public void setPrimaryOrderId(Long primaryOrderId) {
        this.primaryOrderId = primaryOrderId;
    }

    public Long getTakeProfitOrderId() {
        return takeProfitOrderId;
    }

    public void setTakeProfitOrderId(Long takeProfitOrderId) {
        this.takeProfitOrderId = takeProfitOrderId;
    }

    public Long getStopLossOrderId() {
        return stopLossOrderId;
    }

    public void setStopLossOrderId(Long stopLossOrderId) {
        this.stopLossOrderId = stopLossOrderId;
    }

    @Override
    public String toString() {
        return "FullOrder{" + "\n" +
                ", primaryOrder=" + primaryOrder +"\n" +
                ", PO price=" + primaryOrder.getPrice() +"\n" +
                ", PO side=" + primaryOrder.getSide() +"\n" +
                ", takeProfitOrder=" + takeProfitOrder +"\n" +
                ", TP price=" + takeProfitOrder.getPrice() +"\n" +
                ", TP side=" + takeProfitOrder.getSide() +"\n" +
                ", stopLossOrder=" + stopLossOrder +"\n" +
                ", SL price=" + stopLossOrder.getPrice() +"\n" +
                ", SL side=" + stopLossOrder.getSide() +"\n" +
                '}';
    }
    public String toStringV2() {
        return "FullOrder{" +"\n" +
                "primaryFilled=" + primaryFilled +"\n" +
                ", primaryOrder=" + getPrimaryOrder() +"\n" +
                ", primaryOrderId=" + primaryOrderId +"\n" +
                ", isPrimaryPlaced=" + isPrimaryPlaced +"\n" +
                ", takeProfitOrder=" + getTakeProfitOrder() +"\n" +
                ", takeProfitOrderId=" + takeProfitOrderId +"\n" +
                ", isTPPlaced=" + isTPPlaced +"\n" +
                ", TPisFilled=" + TPisFilled +"\n" +
                ", stopLossOrder=" + getStopLossOrder() +"\n" +
                ", stopLossOrderId=" + stopLossOrderId +"\n" +
                ", isSLPlaced=" + isSLPlaced +"\n" +
                ", SLisFilled=" + SLisFilled +"\n" +
                ", isFull=" + isFull +"\n" +
                '}';
    }
}
