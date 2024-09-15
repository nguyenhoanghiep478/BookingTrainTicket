package com.booksms.payment.core.domain.exception;

public class CreatePaymentFailedException extends RuntimeException {
    public CreatePaymentFailedException(String message) {
        super(message);
    }
}
