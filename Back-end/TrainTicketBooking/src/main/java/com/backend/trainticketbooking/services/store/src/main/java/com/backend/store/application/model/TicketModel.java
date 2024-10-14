package com.backend.store.application.model;

import com.backend.store.core.domain.state.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketModel {
    private Integer id;
    private List<Integer> seatIds;
    private Integer departureStationId;
    private Integer arrivalStationId;
    private BigDecimal price;
    private Integer scheduleId;
    private String customerName;
    private String customerEmail;
    private Integer customerId;
    private TicketStatus status;
}
