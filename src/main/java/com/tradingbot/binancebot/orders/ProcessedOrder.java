package com.tradingbot.binancebot.orders;

public class ProcessedOrder {
        private Long orderId;
        private String symbol;
        private String status;
        private String clientOrderId;
        private Double price;
        private Double avgPrice;
        private Double origQty;
        private Double executedQty;
        private Double cumQuote;
        private String timeInForce;
        private String type;
        private Boolean reduceOnly;
        private Boolean closePosition;
        private String side;
        private String positionSide;
        private Double stopPrice;
        private String workingType;
        private Boolean priceProtect;
        private String origType;
        private Long updateTime;

        private String result;

        public ProcessedOrder() {
        }

        public ProcessedOrder(Long orderId, String symbol, String status, String clientOrderId,
                              Double price, Double avgPrice, Double origQty, Double executedQty,
                              Double cumQuote, String timeInForce, String type, Boolean reduceOnly,
                              Boolean closePosition, String side, String positionSide, Double stopPrice,
                              String workingType, Boolean priceProtect, String origType, Long updateTime) {
                this.orderId = orderId;
                this.symbol = symbol;
                this.status = status;
                this.clientOrderId = clientOrderId;
                this.price = price;
                this.avgPrice = avgPrice;
                this.origQty = origQty;
                this.executedQty = executedQty;
                this.cumQuote = cumQuote;
                this.timeInForce = timeInForce;
                this.type = type;
                this.reduceOnly = reduceOnly;
                this.closePosition = closePosition;
                this.side = side;
                this.positionSide = positionSide;
                this.stopPrice = stopPrice;
                this.workingType = workingType;
                this.priceProtect = priceProtect;
                this.origType = origType;
                this.updateTime = updateTime;
        }

        public Long getOrderId() {
                return orderId;
        }

        public void setOrderId(Long orderId) {
                this.orderId = orderId;
        }

        public String getSymbol() {
                return symbol;
        }

        public void setSymbol(String symbol) {
                this.symbol = symbol;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getClientOrderId() {
                return clientOrderId;
        }

        public void setClientOrderId(String clientOrderId) {
                this.clientOrderId = clientOrderId;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public Double getAvgPrice() {
                return avgPrice;
        }

        public void setAvgPrice(Double avgPrice) {
                this.avgPrice = avgPrice;
        }

        public Double getOrigQty() {
                return origQty;
        }

        public void setOrigQty(Double origQty) {
                this.origQty = origQty;
        }

        public Double getExecutedQty() {
                return executedQty;
        }

        public void setExecutedQty(Double executedQty) {
                this.executedQty = executedQty;
        }

        public Double getCumQuote() {
                return cumQuote;
        }

        public void setCumQuote(Double cumQuote) {
                this.cumQuote = cumQuote;
        }

        public String getTimeInForce() {
                return timeInForce;
        }

        public void setTimeInForce(String timeInForce) {
                this.timeInForce = timeInForce;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public Boolean getReduceOnly() {
                return reduceOnly;
        }

        public void setReduceOnly(Boolean reduceOnly) {
                this.reduceOnly = reduceOnly;
        }

        public Boolean getClosePosition() {
                return closePosition;
        }

        public void setClosePosition(Boolean closePosition) {
                this.closePosition = closePosition;
        }

        public String getSide() {
                return side;
        }

        public void setSide(String side) {
                this.side = side;
        }

        public String getPositionSide() {
                return positionSide;
        }

        public void setPositionSide(String positionSide) {
                this.positionSide = positionSide;
        }

        public Double getStopPrice() {
                return stopPrice;
        }

        public void setStopPrice(Double stopPrice) {
                this.stopPrice = stopPrice;
        }

        public String getWorkingType() {
                return workingType;
        }

        public void setWorkingType(String workingType) {
                this.workingType = workingType;
        }

        public Boolean getPriceProtect() {
                return priceProtect;
        }

        public void setPriceProtect(Boolean priceProtect) {
                this.priceProtect = priceProtect;
        }

        public String getOrigType() {
                return origType;
        }

        public void setOrigType(String origType) {
                this.origType = origType;
        }

        public Long getUpdateTime() {
                return updateTime;
        }

        public void setUpdateTime(Long updateTime) {
                this.updateTime = updateTime;
        }

        public String getResult() {
                return result;
        }

        public void setResult(String result) {
                this.result = result;
        }

        @Override
        public String toString() {
                return "ProcessedOrder{" +
                        "orderId=" + orderId +
                        ", symbol='" + symbol + '\'' +
                        ", status='" + status + '\'' +
                        ", clientOrderId='" + clientOrderId + '\'' +
                        ", price=" + price +
                        ", avgPrice=" + avgPrice +
                        ", origQty=" + origQty +
                        ", executedQty=" + executedQty +
                        ", cumQuote=" + cumQuote +
                        ", timeInForce='" + timeInForce + '\'' +
                        ", type='" + type + '\'' +
                        ", reduceOnly=" + reduceOnly +
                        ", closePosition=" + closePosition +
                        ", side='" + side + '\'' +
                        ", positionSide='" + positionSide + '\'' +
                        ", stopPrice=" + stopPrice +
                        ", workingType='" + workingType + '\'' +
                        ", priceProtect=" + priceProtect +
                        ", origType='" + origType + '\'' +
                        ", updateTime=" + updateTime +
                        ", result='" + result + '\'' +
                        '}';
        }
}
