package com.bookms.order.interfaceLayer.DTO;

import com.bookms.order.core.domain.Entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    @NotNull(message = "order number is required")
    private Long orderNumber;
    private Status status;
    private int customerId;
    @NotNull(message = "customer name is required")
    private String customerName;
    private String customerEmail;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
    @NotNull(message = "schedule id is required")
    private Integer scheduleId;
    @NotNull(message = "departure station id is required")
    private Integer departureStationId;
    @NotNull(message = "arrival station id is required")
    private Integer arrivalStationId;
    @NotNull(message = "payment method is required")
    private String paymentMethod;
    private Date createdDate;
    private String token;

}
