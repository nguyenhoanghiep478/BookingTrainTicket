package com.bookms.order.application.model;

import com.bookms.order.core.domain.Entity.OrderType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateQuantityModel {
    private Integer id;
    private int quantity;
    private OrderType type;
}
