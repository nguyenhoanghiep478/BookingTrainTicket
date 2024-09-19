package com.backend.store.core.domain.exception;

public class TrainNotExistedException extends RuntimeException {
    public TrainNotExistedException(String message) {
        super(message);
    }
}
