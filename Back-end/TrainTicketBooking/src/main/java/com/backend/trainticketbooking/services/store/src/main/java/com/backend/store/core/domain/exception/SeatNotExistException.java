package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.SEAT_NOT_EXIST_EXCEPTION;

public class SeatNotExistException extends RuntimeException implements CustomException{
    public SeatNotExistException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return SEAT_NOT_EXIST_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return SEAT_NOT_EXIST_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return SEAT_NOT_EXIST_EXCEPTION.getDescription();
    }
}
