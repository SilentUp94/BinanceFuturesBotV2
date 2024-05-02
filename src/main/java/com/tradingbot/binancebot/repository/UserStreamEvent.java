package com.tradingbot.binancebot.repository;

import com.tradingbot.binancebot.api.IClient;

public class UserStreamEvent implements IClient {
    private static Long count = 0L ;
    private Long eventId;
    private Long orderId;
    private String eventType;
    private String status;

    public UserStreamEvent(String eventType, Long orderId, String status) {
        this.orderId = orderId;
        this.eventType = eventType;
        this.status = status;
        this.eventId = count + 1;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserStreamEvent{" +
                "eventId=" + eventId +
                ", orderId=" + orderId +
                ", eventType='" + eventType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
