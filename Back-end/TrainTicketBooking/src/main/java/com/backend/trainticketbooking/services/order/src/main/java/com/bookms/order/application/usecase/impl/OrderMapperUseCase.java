package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.OrderItems;
import com.bookms.order.core.domain.Entity.Orders;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperUseCase {

    public Orders toOrders(OrdersModel ordersModel){
        Orders orders = new Orders();
        orders.setOrderType(ordersModel.getOrderType());
        orders.setTotalPrice(ordersModel.getTotalPrice());
        orders.setStatus(ordersModel.getStatus());
        orders.setCustomerId(ordersModel.getCustomerId());
        orders.setPaymentMethod(ordersModel.getPaymentMethod());
        orders.setOrderNumber(ordersModel.getOrderNumber());
        orders.setPaymentId(ordersModel.getPaymentId());
        orders.setOrderItems(ordersModel.getOrderItems().stream().map(item -> toOrderItems(item,orders)).toList());
        orders.setShipmentId(ordersModel.getShipmentId());
        return orders;
    }

    public OrderItems toOrderItems(OrderItemModel orderItemModel, Orders orders){
        OrderItems orderItems = new OrderItems();
        orderItems.setBookId(orderItemModel.getBookId());
        orderItems.setTotalQuantity(orderItemModel.getTotalQuantity());
        orderItems.setPrice(orderItemModel.getPrice());
        orderItems.setOrders(orders);
        return orderItems;
    }


}
