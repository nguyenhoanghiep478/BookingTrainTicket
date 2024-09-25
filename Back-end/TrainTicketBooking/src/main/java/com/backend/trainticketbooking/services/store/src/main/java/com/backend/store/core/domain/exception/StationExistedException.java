package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.STATION_EXISTED_EXCEPTION;

public class StationExistedException extends RuntimeException implements CustomException{
    public StationExistedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return STATION_EXISTED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return STATION_EXISTED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return STATION_EXISTED_EXCEPTION.getDescription();
    }
}
