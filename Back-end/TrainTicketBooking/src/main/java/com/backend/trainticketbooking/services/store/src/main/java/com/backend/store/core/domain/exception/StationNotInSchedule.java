package com.backend.store.core.domain.exception;

import com.backend.store.core.domain.exception.exceptionHandler.Error;
import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.STATION_NOT_IN_SCHEDULE;

public class StationNotInSchedule extends RuntimeException implements CustomException{
    public StationNotInSchedule(String message) {
        super(message);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return STATION_NOT_IN_SCHEDULE.getHttpStatus();
    }

    @Override
    public int getCode() {
        return STATION_NOT_IN_SCHEDULE.getCode();
    }

    @Override
    public String getDescription() {
        return STATION_NOT_IN_SCHEDULE.getDescription();
    }
}
