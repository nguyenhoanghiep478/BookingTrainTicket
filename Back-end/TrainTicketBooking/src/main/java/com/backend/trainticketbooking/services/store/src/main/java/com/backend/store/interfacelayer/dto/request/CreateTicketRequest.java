package com.backend.store.interfacelayer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketRequest {
    @NotNull(message = "Seat is required")
    private List<Integer> seatIds;
    @NotNull(message = "Departure station is required")
    private Integer departureStationId;
    @NotNull(message = "Arrival station is required")
    private Integer arrivalStationId;
    @NotNull(message = "Price is required")
    private BigDecimal price;
    @NotNull(message = "Schedule is required")
    private Integer scheduleId;
    @NotNull(message = "Customer name is required")
    private String customerName;
    @NotNull(message = "Customer email is required")
    private String customerEmail;
    private Integer customerId;
}
