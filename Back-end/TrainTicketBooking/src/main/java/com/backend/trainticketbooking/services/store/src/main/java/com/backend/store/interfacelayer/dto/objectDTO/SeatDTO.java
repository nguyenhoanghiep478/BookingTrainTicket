package com.backend.store.interfacelayer.dto.objectDTO;

import com.backend.store.core.domain.state.SeatClass;
import com.backend.store.core.domain.state.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatDTO {
    private Integer id;
    private String seatNumber;
    private SeatClass seatClass;
    private SeatType seatType;
    private Boolean isAvailable;
    private BigDecimal price;
}