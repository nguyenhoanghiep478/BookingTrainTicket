package com.backend.store.interfacelayer.dto.objectDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Integer id;
    private Long orderNumber;
    private Status status;
    private int customerId;
    @NotNull(message = "customer name is required")
    private String customerName;
    private String customerEmail;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
    private Integer scheduleId;
    private Integer departureStationId;
    private Integer arrivalStationId;
    private PaymentMethod paymentMethod;
    private Date createdDate;
    private String token;
    private Boolean isHaveRoundTrip;
    private Integer roundTripScheduleId;
    private List<OrderItemDTO> roundTripItems;
    private Integer ticketId;
}
