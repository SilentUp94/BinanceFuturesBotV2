package com.tradingbot.binancebot.service;

import com.tradingbot.binancebot.enums.LocalFileName;
import com.tradingbot.binancebot.orders.AdditionalOrder;
import com.tradingbot.binancebot.orders.FullOrder;
import com.tradingbot.binancebot.orders.OrderService;
import com.tradingbot.binancebot.orders.OrderSettings;
import com.tradingbot.binancebot.repository.FullOrderRepo;
import com.tradingbot.binancebot.util.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FullOrderRepoServices {
    private final FullOrderRepo fullOrderRepo = FullOrderRepo.getInstance();
    private final OrderService orderService = new OrderService();

    private static final Logger logger = LoggerFactory.getLogger(FullOrderRepoServices.class);

    public FullOrderRepoServices() {
    }

    public FullOrder save(FullOrder fullOrder) {
        fullOrderRepo.getFullOrderList().add(fullOrder);
        return fullOrder;
    }

    public FullOrder remove(FullOrder fullOrder) {
        fullOrderRepo.getFullOrderList().remove(fullOrder);
        return fullOrder;
    }

    public void clearWaitingOrders() {
        logger.info("Clear waitingList");
        if (!fullOrderRepo.getFullOrderList().isEmpty()) {
            FullOrder temp = fullOrderRepo.getFullOrderList().get(fullOrderRepo.getFullOrderList().size() - 1);
            System.out.println(
                    temp.toStringV2()
            );
//                if (temp.getPrimaryPlaced() && !temp.getPrimaryFilled()) {
            if (temp.getPrimaryPlaced() && !temp.getPrimaryFilled()) {
                System.out.println("remove primary order!");
                String primaryOrderClose = orderService.closeOrderById(temp.getPrimaryOrderId(), OrderSettings.SYMBOL);
                Services.writeToFile("Remove primary order", primaryOrderClose, LocalFileName.TRADES);
            }
            if (temp.getTPPlaced() && !temp.getTPisFilled()) {
                String TPOrderClose = orderService.closeOrderById(temp.getTakeProfitOrderId(), OrderSettings.SYMBOL);
                Services.writeToFile("Remove TP order", TPOrderClose, LocalFileName.TRADES);
            }
            if (temp.getSLPlaced() && !temp.getSLisFilled()) {
                String SLOrderClose = orderService.closeOrderById(temp.getStopLossOrderId(), OrderSettings.SYMBOL);
                Services.writeToFile("Remove SL order", SLOrderClose, LocalFileName.TRADES);
            }
            fullOrderRepo.getFullOrderList().clear();
        }
    }

    public void setFull(FullOrder fullOrder) {
        fullOrder.setFull(fullOrder.getPrimaryPlaced() &&
                fullOrder.getTPPlaced() &&
                fullOrder.getSLPlaced());
    }

//    public void displayList(List<ProcessedOrder> list) {
//        System.out.println("---------------------------");
//        if (list.isEmpty()) {
//            System.out.println("Empty list");
//            return;
//        }
//        list.forEach(System.out::println);
//    }

//    public Optional<ProcessedOrder> findById(List<ProcessedOrder> orderList, Long orderId) {
//        return orderList.stream().filter(o -> Objects.equals(o.getOrderId(), orderId)).findFirst();
//    }
}
