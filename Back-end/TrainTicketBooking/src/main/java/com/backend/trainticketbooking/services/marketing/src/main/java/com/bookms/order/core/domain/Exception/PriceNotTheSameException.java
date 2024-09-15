package com.bookms.order.core.domain.Exception;

public class PriceNotTheSameException extends RuntimeException {
    public PriceNotTheSameException(String message) {
        super(message);
    }
}
