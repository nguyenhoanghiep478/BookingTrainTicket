package com.bookms.order.application.model;

import com.bookms.order.core.domain.Entity.OrderItems;
import com.bookms.order.core.domain.Entity.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderSearchCriteria {
    private Integer id;
    private Long orderNumber;
    private Status status;
    private Integer customerId;
    private BigDecimal totalPrice;
    private List<OrderItems> orderItems;
    private String paymentMethod;
}
