package com.booksms.payment.interfaceLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer bookId;
    private String name;
    private BigDecimal price;
    private Integer totalQuantity;
}
