package com.bookms.order.interfaceLayer.DTO;

import com.bookms.order.core.domain.Entity.Status;
import lombok.Data;

@Data
public class ResponsePayment {
    private Long orderNumber;
    private String paymentMethod;
    private Integer paymentId;
    private Status status;
}
