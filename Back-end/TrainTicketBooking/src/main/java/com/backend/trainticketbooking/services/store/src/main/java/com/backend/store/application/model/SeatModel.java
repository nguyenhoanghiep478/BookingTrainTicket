package com.backend.store.application.model;

import com.backend.store.core.domain.state.SeatClass;
import com.backend.store.core.domain.state.SeatType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
