package com.bookms.order.application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateQuantityModel {
    private Integer id;
    private int quantity;
}
