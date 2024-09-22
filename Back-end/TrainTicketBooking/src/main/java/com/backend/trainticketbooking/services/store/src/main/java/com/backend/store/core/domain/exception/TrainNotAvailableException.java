package com.backend.store.core.domain.exception;

public class TrainNotAvailableException extends RuntimeException {
    public TrainNotAvailableException(String message) {
        super(message);
    }
}
