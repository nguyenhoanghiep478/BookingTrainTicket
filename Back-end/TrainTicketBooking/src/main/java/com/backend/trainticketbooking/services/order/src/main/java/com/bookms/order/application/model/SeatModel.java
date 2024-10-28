package com.bookms.order.application.model;

import com.bookms.order.core.domain.State.SeatClass;
import com.bookms.order.core.domain.State.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatModel {
    private Integer id;
    private String seatNumber;
    private SeatClass seatClass;
    private SeatType seatType;
    private Boolean isAvailable;
    private BigDecimal price;
}
