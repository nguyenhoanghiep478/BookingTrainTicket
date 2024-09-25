package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.TRAIN_EXISTED_EXCEPTION;

public class TrainExistedException extends RuntimeException implements CustomException{
    public TrainExistedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return TRAIN_EXISTED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return TRAIN_EXISTED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return TRAIN_EXISTED_EXCEPTION.getDescription();
    }
}
