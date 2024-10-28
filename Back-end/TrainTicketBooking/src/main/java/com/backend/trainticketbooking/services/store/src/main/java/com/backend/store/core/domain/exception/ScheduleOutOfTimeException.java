package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.SCHEDULE_OUT_OF_TIME_EXCEPTION;

public class ScheduleOutOfTimeException extends RuntimeException implements CustomException{
    public ScheduleOutOfTimeException(String message) {
        super(message);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return SCHEDULE_OUT_OF_TIME_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return SCHEDULE_OUT_OF_TIME_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return SCHEDULE_OUT_OF_TIME_EXCEPTION.getDescription();
    }
}
