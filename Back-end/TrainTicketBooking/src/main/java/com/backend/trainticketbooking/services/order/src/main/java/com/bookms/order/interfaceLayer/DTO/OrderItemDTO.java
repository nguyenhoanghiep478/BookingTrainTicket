package com.bookms.order.interfaceLayer.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer seatId;
    private String name;
    private BigDecimal price;
}
