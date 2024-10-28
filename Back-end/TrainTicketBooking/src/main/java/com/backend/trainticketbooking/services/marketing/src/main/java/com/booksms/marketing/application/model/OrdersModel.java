package com.booksms.marketing.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersModel {
    private Long orderNumber;
    private OrderType orderType;
    private Status status;
    private int customerId;
    private String customerEmail;
    private BigDecimal totalPrice;
    private List<OrderItemModel> orderItems;
    private String paymentMethod;
    private List<BookModel> bookModels;
    private Integer paymentId;
}
