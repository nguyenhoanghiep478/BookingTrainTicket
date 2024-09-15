package com.bookms.order.interfaceLayer.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer bookId;
    private String name;
    private BigDecimal price;
    private Integer totalQuantity;
}
