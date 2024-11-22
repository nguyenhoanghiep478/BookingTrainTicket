package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.TRAIN_NOT_EXISTED_EXCEPTION;

public class TrainNotExistedException extends RuntimeException implements CustomException{
    public TrainNotExistedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return TRAIN_NOT_EXISTED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return TRAIN_NOT_EXISTED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return TRAIN_NOT_EXISTED_EXCEPTION.getDescription();
    }
}
