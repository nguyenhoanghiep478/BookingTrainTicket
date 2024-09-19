package com.backend.store.core.domain.exception;

public class TrainExistedException extends RuntimeException {
    public TrainExistedException(String message) {
        super(message);
    }
}
