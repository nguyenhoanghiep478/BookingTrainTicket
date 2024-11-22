package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.ROUTE_EXISTED_EXCEPTION;

public class RouteExistedException extends RuntimeException implements CustomException{
    public RouteExistedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return ROUTE_EXISTED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return ROUTE_EXISTED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return ROUTE_EXISTED_EXCEPTION.getDescription();
    }
}
