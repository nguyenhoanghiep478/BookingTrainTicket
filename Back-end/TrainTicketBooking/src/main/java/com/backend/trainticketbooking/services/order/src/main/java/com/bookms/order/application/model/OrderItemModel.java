package com.bookms.order.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemModel {
    private Integer orderId;
    private Integer bookId;
    private String name;
    private BigDecimal price;
    private Integer totalQuantity;
}
