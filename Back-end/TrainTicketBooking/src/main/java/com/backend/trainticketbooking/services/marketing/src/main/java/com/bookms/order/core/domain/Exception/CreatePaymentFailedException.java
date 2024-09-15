package com.bookms.order.core.domain.Exception;

public class CreatePaymentFailedException extends RuntimeException {
    public CreatePaymentFailedException(String message) {
        super(message);
    }
}
