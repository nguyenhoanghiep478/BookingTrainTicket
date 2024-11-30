package com.bookms.order.web.mapper;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.OrderItemDTO;
import com.bookms.order.core.domain.Entity.OrderItems;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class OrderMapper {
    public static Order toEntity(OrderDTO orderDTO) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        BigDecimal price;
        for(OrderItemDTO item : orderDTO.getOrderItems()) {
            price = item.getPrice();
            totalPrice = totalPrice.add(price);
        }
        Order order = Order.builder()
                .orderNumber(orderDTO.getOrderNumber())
                .paymentMethod(orderDTO.getPaymentMethod().getValue())
                .status(orderDTO.getStatus())
                .totalPrice(totalPrice)
                .orderItems(orderDTO.getOrderItems().stream()
                        .map(item -> OrderItems
                                .builder()
                                .price(item.getPrice())
                                .seatId(item.getSeatId())
                                .build())
                        .toList())
                .build();
        for (OrderItems item : order.getOrderItems()) {
            item.setOrder(order);
        }
        return order;
    }
    public static OrderDTO toDTO(OrdersModel orders) {
        return OrderDTO.builder()
                .orderNumber(orders.getOrderNumber())
                .paymentMethod(orders.getPaymentMethod())
                .status(orders.getStatus())
                .totalPrice(orders.getTotalPrice())
                .orderItems(orders.getOrderItems().stream().map(item -> OrderItemDTO.builder()
                                .price(item.getPrice())
                                .name(item.getName())
                                .build())
                        .toList()
                )
                .build();
    }

}
