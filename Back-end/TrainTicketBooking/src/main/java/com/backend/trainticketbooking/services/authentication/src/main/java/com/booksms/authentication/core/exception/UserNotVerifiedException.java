package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

import static com.booksms.authentication.core.exception.Error.USER_NOT_VERIFIED_EXCEPTION;

public class UserNotVerifiedException extends RuntimeException implements CustomException{
   public UserNotVerifiedException(final String message){
       super(message);
   }

    @Override
    public HttpStatus getHttpStatus() {
        return USER_NOT_VERIFIED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return USER_NOT_VERIFIED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return USER_NOT_VERIFIED_EXCEPTION.getDescription();
    }
}
