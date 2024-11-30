package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

import static com.booksms.authentication.core.exception.Error.PASSWORD_THE_SAME_WITH_OLD_PASSWORD;

public class PasswordTheSameWithOldPassWordException extends RuntimeException implements CustomException{
    public PasswordTheSameWithOldPassWordException(String message){
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return PASSWORD_THE_SAME_WITH_OLD_PASSWORD.getHttpStatus();
    }

    @Override
    public int getCode() {
        return PASSWORD_THE_SAME_WITH_OLD_PASSWORD.getCode();
    }

    @Override
    public String getDescription() {
        return PASSWORD_THE_SAME_WITH_OLD_PASSWORD.getDescription();
    }
}
