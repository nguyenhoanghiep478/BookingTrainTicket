package com.bookms.order.interfaceLayer.DTO;

import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotNull(message = "orderType is required")
    private OrderType orderType;
    private Status status;
    @NotNull(message = "customer id is required")
    private int customerId;
    private String customerName;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
    @NotNull(message = "payment method is required")
    private String paymentMethod;
    private Date createdDate;
    private String token;
    @NotNull(message = "shipment service is required")
    private Integer shipmentServiceId;
    @NotNull(message = "origin address is required")
    private Integer originAddressId;
    @NotNull(message = "destination address is required")
    private String destinationAddress;
    @NotNull(message = "shipment fee is required")
    private double shipmentFee;
    @NotNull(message = "distance is required for shipment")
    private double distance;
}
