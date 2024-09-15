package com.bookms.order.interfaceLayer.DTO.Request;

import com.bookms.order.core.domain.Entity.OrderType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuantityDTO {
    private int id;
    private int quantity;
    private OrderType type;
}
