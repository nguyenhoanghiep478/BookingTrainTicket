package com.backend.store.core.domain.exception;

public class RouteExistedException extends RuntimeException {
    public RouteExistedException(String message) {
        super(message);
    }
}
