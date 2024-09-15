package com.bookms.order.core.domain.Exception;

public class BookNotInStockException extends RuntimeException {
    public BookNotInStockException(String message) {
        super(message);
    }
}
