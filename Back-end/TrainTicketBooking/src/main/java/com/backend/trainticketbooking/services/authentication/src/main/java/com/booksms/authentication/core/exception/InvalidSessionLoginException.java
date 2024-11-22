package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

import static com.booksms.authentication.core.exception.Error.INVALID_SESSION_LOGIN_EXCEPTION;

public class InvalidSessionLoginException extends RuntimeException implements CustomException{
    public InvalidSessionLoginException(String message) {
        super(message);
    }


    @Override
    public HttpStatus getHttpStatus() {
        return INVALID_SESSION_LOGIN_EXCEPTION.httpStatus;
    }

    @Override
    public int getCode() {
        return INVALID_SESSION_LOGIN_EXCEPTION.code;
    }

    @Override
    public String getDescription() {
        return INVALID_SESSION_LOGIN_EXCEPTION.description;
    }
}
