package com.backend.store.core.domain.exception;

import com.backend.store.core.domain.exception.exceptionHandler.Error;
import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.INVALID_STOP_SEQUENCE_EXCEPTION;

public class InvalidStopSequenceException extends RuntimeException implements CustomException{
    public InvalidStopSequenceException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return INVALID_STOP_SEQUENCE_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return INVALID_STOP_SEQUENCE_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return INVALID_STOP_SEQUENCE_EXCEPTION.getDescription();
    }
}
