package com.bookms.order.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.NotNull;
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
    private int id;
    private List<Integer> seatIds;
    private Integer departureStationId;
    private Integer arrivalStationId;
    private BigDecimal price;
    private Integer scheduleId;
    private String customerName;
    private String customerEmail;
    private Integer customerId;
    private Timestamp departureTime;
    private Long orderNumber;
}
