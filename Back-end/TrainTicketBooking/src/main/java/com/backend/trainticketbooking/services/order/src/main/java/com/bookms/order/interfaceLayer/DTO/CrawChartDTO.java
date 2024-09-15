package com.bookms.order.interfaceLayer.DTO;

import com.bookms.order.core.domain.State.DateOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrawChartDTO {
    private DateOfWeek dateOfWeek;
    private BigDecimal totalPrice;
    private Integer bookId;
    private Integer quantity;
}
