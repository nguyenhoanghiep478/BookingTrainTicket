package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.SeatModel;
import com.bookms.order.core.domain.Entity.OrderItems;
import com.bookms.order.core.domain.Entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapperUseCase {

    private final ModelMapper modelMapper;

    public OrderMapperUseCase(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order toOrders(OrdersModel ordersModel){
        Order order = new Order();
        order.setTotalPrice(ordersModel.getTotalPrice());
        order.setStatus(ordersModel.getStatus());
        order.setCustomerId(ordersModel.getCustomerId());
        order.setPaymentMethod(ordersModel.getPaymentMethod().getValue());
        order.setTicketId(ordersModel.getTicketId());
        order.setOrderNumber(ordersModel.getOrderNumber());
        order.setPaymentId(ordersModel.getPaymentId());
        order.setOrderItems(ordersModel.getOrderItems().stream().map(item -> toOrderItems(item, order)).toList());
        return order;
    }

    public List<Order>   toOrdersWithRoundTrip(OrdersModel ordersModel){
        List<Order> orders = new ArrayList<>();

        List<OrderItemModel> forwardSeats = new ArrayList<>();
        List<OrderItemModel> roundTripSeats = new ArrayList<>();

        int sizePerSchedule = ordersModel.getOrderItems().size()/2;
        for(int i = 0; i < sizePerSchedule; i++){
            forwardSeats.add(ordersModel.getOrderItems().get(i));
            roundTripSeats.add(ordersModel.getOrderItems().get(i+sizePerSchedule));
        }
        OrdersModel temp = modelMapper.map(ordersModel, OrdersModel.class);

        OrdersModel forwardOrdersModel = temp;
        forwardOrdersModel.setOrderItems(forwardSeats);
        forwardOrdersModel.setTotalPrice(ordersModel.getTotalPrice().subtract(ordersModel.getRoundTripTotalPrice()));
        Order forwardOrder = toOrders(forwardOrdersModel);
        OrdersModel roundTripOrdersModel = temp;
        roundTripOrdersModel.setOrderItems(roundTripSeats);
        roundTripOrdersModel.setTotalPrice(ordersModel.getRoundTripTotalPrice());
        roundTripOrdersModel.setTicketId(ordersModel.getRoundTripTicketId());
        roundTripOrdersModel.setOrderNumber(ordersModel.getOrderNumber()+1);


        Order roundTripOrder = toOrders(roundTripOrdersModel);

        orders.add(forwardOrder);
        orders.add(roundTripOrder);

        return orders;
    }

    public OrderItems toOrderItems(OrderItemModel orderItemModel, Order order){
        OrderItems orderItems = new OrderItems();
        orderItems.setSeatId(orderItemModel.getSeatId());
        orderItems.setPrice(orderItemModel.getPrice());
        orderItems.setOrder(order);
        return orderItems;
    }


}
