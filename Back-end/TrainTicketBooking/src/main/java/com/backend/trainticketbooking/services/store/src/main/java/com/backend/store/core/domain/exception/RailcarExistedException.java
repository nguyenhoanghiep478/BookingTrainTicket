package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.RAILCAR_EXISTED_EXCEPTION;

public class RailcarExistedException extends RuntimeException implements CustomException{
    private String message;
    public RailcarExistedException(String message) {
        super(message);
    }



    @Override
    public HttpStatus getHttpStatus() {
        return RAILCAR_EXISTED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return RAILCAR_EXISTED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return RAILCAR_EXISTED_EXCEPTION.getDescription();
    }
}
