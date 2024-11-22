package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

import static com.booksms.authentication.core.exception.Error.USER_BLOCKED_EXCEPTION;

public class UserBlockedException extends RuntimeException implements CustomException{
    public UserBlockedException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return USER_BLOCKED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return USER_BLOCKED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return USER_BLOCKED_EXCEPTION.getDescription();
    }
}
