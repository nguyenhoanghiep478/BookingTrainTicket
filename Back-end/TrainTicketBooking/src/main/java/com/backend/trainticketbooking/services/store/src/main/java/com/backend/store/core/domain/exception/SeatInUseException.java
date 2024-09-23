package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.SEAT_IN_USE_EXCEPTION;

public class SeatInUseException extends RuntimeException implements CustomException{
    public SeatInUseException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return SEAT_IN_USE_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return SEAT_IN_USE_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return SEAT_IN_USE_EXCEPTION.getDescription();
    }
}
