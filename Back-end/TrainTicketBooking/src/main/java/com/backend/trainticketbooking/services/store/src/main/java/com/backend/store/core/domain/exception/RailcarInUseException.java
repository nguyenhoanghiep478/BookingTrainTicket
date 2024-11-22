package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.RAILCAR_IN_USE_EXCEPTION;

public class RailcarInUseException extends RuntimeException implements CustomException{
    @Override
    public HttpStatus getHttpStatus() {
        return RAILCAR_IN_USE_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return RAILCAR_IN_USE_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return RAILCAR_IN_USE_EXCEPTION.getDescription();
    }

    public RailcarInUseException(String message) {
        super(message);
    }
}
