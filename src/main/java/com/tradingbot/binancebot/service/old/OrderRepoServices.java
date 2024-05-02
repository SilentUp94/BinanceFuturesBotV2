package com.tradingbot.binancebot.service.old;

public class OrderRepoServices {
//    private final OrderRepository orderRepository = OrderRepository.getInstance();
//    private final OrderService orderService = new OrderService();
//
//    public OrderRepoServices() {
//    }
//
//    public ProcessedOrder save(ProcessedOrder processedOrder) {
//        switch (processedOrder.getStatus()) {
//            case "NEW" -> orderRepository.getNewOrdersList().add(processedOrder);
//            case "FILLED" -> orderRepository.getActiveOrdersList().add(processedOrder);
//            case "CANCELED", "EXPIRED" -> orderRepository.getClosedOrdersList().add(processedOrder);
//            default -> System.out.println("Order cannot be stored!");
//        }
//        return processedOrder;
//    }
//
//    public ProcessedOrder remove(ProcessedOrder processedOrder) {
//        switch (processedOrder.getStatus()) {
//            case "NEW" -> orderRepository.getNewOrdersList().remove(processedOrder);
//            case "FILLED" -> orderRepository.getActiveOrdersList().remove(processedOrder);
//            case "CANCELED", "EXPIRED" -> orderRepository.getClosedOrdersList().remove(processedOrder);
//            default -> System.out.println("Order cannot be removed from list!");
//        }
//        return processedOrder;
//    }
//
//    public void clearWaitingOrders() {
//        System.out.println(" new orders: ");
//        displayList(orderRepository.getNewOrdersList());
//
//        System.out.println(" active orders: ");
//        displayList(orderRepository.getActiveOrdersList());
//
//        System.out.println(" close orders: ");
//        displayList(orderRepository.getClosedOrdersList());
//
//        System.out.println("========================================================================");
//        if (!orderRepository.getNewOrdersList().isEmpty()) {
//            List<ProcessedOrder> tempOrderList = orderRepository.getNewOrdersList();
//            Iterator<ProcessedOrder> iterator = tempOrderList.iterator();
//            while (iterator.hasNext()) {
//                ProcessedOrder processedOrder = iterator.next();
//                String closeResult = orderService.closeOrderById(processedOrder.getOrderId(), processedOrder.getSymbol());
//                iterator.remove();
//                ProcessedOrder removedOrder = orderService.createProcessedOrder(closeResult);
//                save(removedOrder);
//            }
//        }
//    }
//
//    public void displayList(List<ProcessedOrder> list) {
//        System.out.println("---------------------------");
//        if (list.isEmpty()) {
//            System.out.println("Empty list");
//            return;
//        }
//        list.forEach(System.out::println);
//    }
//
//    public Optional<ProcessedOrder> findById(List<ProcessedOrder> orderList, Long orderId) {
//        return orderList.stream().filter(o -> Objects.equals(o.getOrderId(), orderId)).findFirst();
//    }


}
