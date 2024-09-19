package com.backend.store.core.domain.exception;

public class RailcarNotExistException extends RuntimeException{
    public RailcarNotExistException(String message) {
        super(message);
    }
}
