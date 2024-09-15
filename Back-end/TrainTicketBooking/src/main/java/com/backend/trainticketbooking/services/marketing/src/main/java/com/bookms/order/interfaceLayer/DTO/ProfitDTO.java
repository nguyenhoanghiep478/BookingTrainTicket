package com.bookms.order.interfaceLayer.DTO;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProfitDTO {
    private Integer bookId;
    private BigDecimal profit;
}
