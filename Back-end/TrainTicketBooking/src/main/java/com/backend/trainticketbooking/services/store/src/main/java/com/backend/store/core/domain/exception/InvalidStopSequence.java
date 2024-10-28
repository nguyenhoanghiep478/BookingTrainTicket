package com.backend.store.core.domain.exception;

import com.backend.store.core.domain.exception.exceptionHandler.Error;
import org.springframework.http.HttpStatus;

public class InvalidStopSequence extends RuntimeException implements CustomException{
    public InvalidStopSequence(final String message){
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return Error.INVALID_STOP_SEQUENCE_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return Error.INVALID_STOP_SEQUENCE_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return Error.INVALID_STOP_SEQUENCE_EXCEPTION.getDescription();
    }
}
