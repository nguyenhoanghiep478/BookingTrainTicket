package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.OrderItems;
import com.bookms.order.core.domain.Entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperUseCase {

    public Order toOrders(OrdersModel ordersModel){
        Order order = new Order();
        order.setTotalPrice(ordersModel.getTotalPrice());
        order.setStatus(ordersModel.getStatus());
        order.setCustomerId(ordersModel.getCustomerId());
        order.setPaymentMethod(ordersModel.getPaymentMethod());
        order.setTicketId(ordersModel.getTicketId());
        order.setOrderNumber(ordersModel.getOrderNumber());
        order.setPaymentId(ordersModel.getPaymentId());
        order.setOrderItems(ordersModel.getOrderItems().stream().map(item -> toOrderItems(item, order)).toList());
        return order;
    }

    public OrderItems toOrderItems(OrderItemModel orderItemModel, Order order){
        OrderItems orderItems = new OrderItems();
        orderItems.setSeatId(orderItemModel.getSeatId());
        orderItems.setPrice(orderItemModel.getPrice());
        orderItems.setOrder(order);
        return orderItems;
    }


}
