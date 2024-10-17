package com.bookms.order.application.model;

import com.bookms.order.core.domain.Entity.Status;
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
public class OrdersModel {
    private String customerEmail;
    private Long orderNumber;
    private Status status;
    private Integer customerId;
    private String customerName;
    private Integer ticketId;
    private BigDecimal totalPrice;
    private List<OrderItemModel> orderItems;
    private Integer scheduleId;
    private Integer departureStationId;
    private Integer arrivalStationId;
    private String paymentMethod;
    private List<BookModel> bookModels;
    private List<SeatModel> seatModels;
    private Integer paymentId;
}
