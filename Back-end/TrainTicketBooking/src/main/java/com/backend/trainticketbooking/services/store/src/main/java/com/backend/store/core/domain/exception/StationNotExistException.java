package com.backend.store.core.domain.exception;

public class StationNotExistException extends RuntimeException {
    public StationNotExistException(String message) {
        super(message);
    }
}
