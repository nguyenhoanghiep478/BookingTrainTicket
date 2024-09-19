package com.backend.store.core.domain.exception;

public class SeatNotExistException extends RuntimeException {
    public SeatNotExistException(String message) {
        super(message);
    }
}
