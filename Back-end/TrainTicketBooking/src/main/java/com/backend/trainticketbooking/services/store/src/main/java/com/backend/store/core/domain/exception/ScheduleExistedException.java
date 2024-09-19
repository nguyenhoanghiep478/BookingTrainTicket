package com.backend.store.core.domain.exception;

public class ScheduleExistedException extends RuntimeException{
    public ScheduleExistedException(String message){
        super(message);
    }
}
