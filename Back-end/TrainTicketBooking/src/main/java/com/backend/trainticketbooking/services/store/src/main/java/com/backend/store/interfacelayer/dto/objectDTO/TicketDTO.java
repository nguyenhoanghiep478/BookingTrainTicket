package com.backend.store.interfacelayer.dto.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {
    private String departureStationName;
    private String arrivalStationName;
    private Timestamp departureTime;
    private List<String> seatNumber;
    private BigDecimal price;
    private String trainName;

}
