package com.booksms.payment.application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsePaymentPageModel {
    private String status;
    private String message;
}
