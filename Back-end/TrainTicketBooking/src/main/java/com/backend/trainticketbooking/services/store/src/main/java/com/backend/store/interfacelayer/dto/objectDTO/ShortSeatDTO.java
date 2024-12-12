package com.backend.store.interfacelayer.dto.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortSeatDTO {
    private Integer id;
    private String seatNumber;
    private Boolean isAvailable;
    private BigDecimal price;
}
