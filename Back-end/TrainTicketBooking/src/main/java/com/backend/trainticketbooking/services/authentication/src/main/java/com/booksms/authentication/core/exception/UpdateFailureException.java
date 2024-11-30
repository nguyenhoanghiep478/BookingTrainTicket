package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

public class UpdateFailureException extends RuntimeException implements CustomException{
    public UpdateFailureException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public int getCode() {
        return 400;
    }

    @Override
    public String getDescription() {
        return "Your password is incorrect. Please try again.";
    }
}
