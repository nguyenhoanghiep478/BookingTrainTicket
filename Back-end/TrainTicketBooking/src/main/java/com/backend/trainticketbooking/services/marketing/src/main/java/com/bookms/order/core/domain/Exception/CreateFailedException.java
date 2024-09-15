package com.bookms.order.core.domain.Exception;

public class CreateFailedException extends RuntimeException {
    public CreateFailedException(String message) {
        super(message);
    }
}
