package com.tradingbot.binancebot.orders;

import com.tradingbot.binancebot.enums.order.*;

public abstract class EntityOrder {

    private String symbol = OrderSettings.SYMBOL;
    private OrderSide side;
    private PositionSide positionSide;
    private OrderType type;
    private TimeInForce timeInForce = OrderSettings.TIME_IN_FORCE;
    private Double quantity = OrderSettings.QUANTITY;
    private Boolean reduceOnly;
    private Double price;
    private String newClientOrderId;
    private Double stopPrice;
    private String closePosition;
    private Double activationPrice;
    private Double callBackRate;
    private OrderWorkingType workingType;
    private String priceProtect;
    private NewOrderRespType newOrderRespType;
    private Long recvWindow;
    private Long timestamp;

    public EntityOrder() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;

    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public PositionSide getPositionSide() {
        return positionSide;
    }

    public void setPositionSide(PositionSide positionSide) {
        this.positionSide = positionSide;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Boolean getReduceOnly() {
        return reduceOnly;
    }

    public void setReduceOnly(Boolean reduceOnly) {
        this.reduceOnly = reduceOnly;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNewClientOrderId() {
        return newClientOrderId;
    }

    public void setNewClientOrderId(String newClientOrderId) {
        this.newClientOrderId = newClientOrderId;
    }

    public Double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(Double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getClosePosition() {
        return closePosition;
    }

    public void setClosePosition(String closePosition) {
        this.closePosition = closePosition;
    }

    public Double getActivationPrice() {
        return activationPrice;
    }

    public void setActivationPrice(Double activationPrice) {
        this.activationPrice = activationPrice;
    }

    public Double getCallBackRate() {
        return callBackRate;
    }

    public void setCallBackRate(Double callBackRate) {
        this.callBackRate = callBackRate;
    }

    public OrderWorkingType getWorkingType() {
        return workingType;
    }

    public void setWorkingType(OrderWorkingType workingType) {
        this.workingType = workingType;
    }

    public String getPriceProtect() {
        return priceProtect;
    }

    public void setPriceProtect(String priceProtect) {
        this.priceProtect = priceProtect;
    }

    public NewOrderRespType getNewOrderRespType() {
        return newOrderRespType;
    }

    public void setNewOrderRespType(NewOrderRespType newOrderRespType) {
        this.newOrderRespType = newOrderRespType;
    }

    public Long getRecvWindow() {
        return recvWindow;
    }

    public void setRecvWindow(Long recvWindow) {
        this.recvWindow = recvWindow;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "EntityOrder{" +
                "symbol='" + symbol + '\'' +
                ", side=" + side +
                ", positionSide=" + positionSide +
                ", type=" + type +
                ", timeInForce=" + timeInForce +
                ", quantity=" + quantity +
                ", reduceOnly=" + reduceOnly +
                ", price=" + price +
                ", newClientOrderId='" + newClientOrderId + '\'' +
                ", stopPrice=" + stopPrice +
                ", closePosition='" + closePosition + '\'' +
                ", activationPrice=" + activationPrice +
                ", callBackRate=" + callBackRate +
                ", workingType=" + workingType +
                ", priceProtect='" + priceProtect + '\'' +
                ", newOrderRespType=" + newOrderRespType +
                ", recvWindow=" + recvWindow +
                ", timestamp=" + timestamp +
                '}';
    }
}
