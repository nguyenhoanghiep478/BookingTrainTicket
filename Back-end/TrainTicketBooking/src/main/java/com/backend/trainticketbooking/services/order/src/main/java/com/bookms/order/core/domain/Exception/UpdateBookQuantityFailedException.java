package com.bookms.order.core.domain.Exception;

public class UpdateBookQuantityFailedException extends RuntimeException {
    public UpdateBookQuantityFailedException(String message) {
        super(message);
    }
}
