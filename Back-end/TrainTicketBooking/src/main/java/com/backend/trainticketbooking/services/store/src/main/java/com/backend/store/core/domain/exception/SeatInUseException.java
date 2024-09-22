package com.backend.store.core.domain.exception;

public class SeatInUseException extends RuntimeException {
    public SeatInUseException(String message) {
        super(message);
    }
}
