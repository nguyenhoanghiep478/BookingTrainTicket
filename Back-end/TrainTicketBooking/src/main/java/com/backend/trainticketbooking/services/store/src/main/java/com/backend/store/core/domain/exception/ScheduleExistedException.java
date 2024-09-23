package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.SCHEDULE_EXISTED_EXCEPTION;

public class ScheduleExistedException extends RuntimeException implements CustomException{
    public ScheduleExistedException(String message){
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return SCHEDULE_EXISTED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return SCHEDULE_EXISTED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return SCHEDULE_EXISTED_EXCEPTION.getDescription();
    }
}
