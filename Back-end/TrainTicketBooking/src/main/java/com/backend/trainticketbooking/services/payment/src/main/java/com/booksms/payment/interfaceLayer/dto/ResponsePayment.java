package com.booksms.payment.interfaceLayer.dto;

import com.booksms.payment.application.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayment {
    private Integer paymentId;
    private Long orderNumber;
    private String paymentMethod;
    private Status status;
}
