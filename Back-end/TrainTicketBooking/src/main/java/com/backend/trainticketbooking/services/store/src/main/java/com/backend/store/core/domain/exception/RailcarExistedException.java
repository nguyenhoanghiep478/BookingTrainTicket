package com.backend.store.core.domain.exception;

public class RailcarExistedException extends RuntimeException {
    public RailcarExistedException(String message) {
        super(message);
    }
}
