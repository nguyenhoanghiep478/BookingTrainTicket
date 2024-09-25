package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.RAILCAR_NOT_EXIST_EXCEPTION;

public class RailcarNotExistException extends RuntimeException implements CustomException{
    @Override
    public HttpStatus getHttpStatus() {
        return RAILCAR_NOT_EXIST_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return RAILCAR_NOT_EXIST_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return RAILCAR_NOT_EXIST_EXCEPTION.getDescription();
    }

    public RailcarNotExistException(String message) {
        super(message);
    }
}
