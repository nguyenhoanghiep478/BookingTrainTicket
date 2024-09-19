package com.backend.store.core.domain.exception;

public class StationExistedException extends RuntimeException {
    public StationExistedException(String message) {
        super(message);
    }
}
