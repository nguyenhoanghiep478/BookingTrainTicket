package com.bookms.order.web.mapper;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.OrderItemDTO;
import com.bookms.order.core.domain.Entity.OrderItems;
import com.bookms.order.core.domain.Entity.Orders;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class OrderMapper {
    public static Orders toEntity(OrderDTO orderDTO) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        BigDecimal price;
        for(OrderItemDTO item : orderDTO.getOrderItems()) {
            price = item.getPrice();
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(item.getTotalQuantity())));
        }
        Orders orders = Orders.builder()
                .orderNumber(orderDTO.getOrderNumber())
                .paymentMethod(orderDTO.getPaymentMethod())
                .orderType(orderDTO.getOrderType())
                .status(orderDTO.getStatus())
                .totalPrice(totalPrice)
                .orderItems(orderDTO.getOrderItems().stream()
                        .map(item -> OrderItems
                                .builder()
                                .price(item.getPrice())
                                .totalQuantity(item.getTotalQuantity())
                                .bookId(item.getBookId())
                                .build())
                        .toList())
                .build();
        for (OrderItems item : orders.getOrderItems()) {
            item.setOrders(orders);
        }
        return orders;
    }
    public static OrderDTO toDTO(OrdersModel orders) {
        return OrderDTO.builder()
                .orderNumber(orders.getOrderNumber())
                .paymentMethod(orders.getPaymentMethod())
                .status(orders.getStatus())
                .orderType(orders.getOrderType())
                .totalPrice(orders.getTotalPrice())
                .orderItems(orders.getOrderItems().stream().map(item -> OrderItemDTO.builder()
                                .price(item.getPrice())
                                .name(item.getName())
                                .totalQuantity(item.getTotalQuantity())
                                .build())
                        .toList()
                )
                .build();
    }

}
