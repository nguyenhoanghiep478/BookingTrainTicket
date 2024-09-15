package com.bookms.order.core.domain.Exception;

public class TotalPriceNotTheSameException extends RuntimeException {
    public TotalPriceNotTheSameException(String message) {
        super(message);
    }
}
