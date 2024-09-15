package com.booksms.payment.interfaceLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    private Long orderNumber;
    private String orderType;
    private String status;
    private int customerId;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
    private String paymentMethod;
}
