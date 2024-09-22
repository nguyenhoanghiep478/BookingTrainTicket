package com.backend.store.core.domain.exception;

public class ScheduleNotExistException extends RuntimeException{
    public ScheduleNotExistException(String message) {
        super(message);
    }
}
