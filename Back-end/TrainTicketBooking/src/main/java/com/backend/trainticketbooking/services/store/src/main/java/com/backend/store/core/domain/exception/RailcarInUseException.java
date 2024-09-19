package com.backend.store.core.domain.exception;

public class RailcarInUseException extends RuntimeException {
    public RailcarInUseException(String message) {
        super(message);
    }
}
