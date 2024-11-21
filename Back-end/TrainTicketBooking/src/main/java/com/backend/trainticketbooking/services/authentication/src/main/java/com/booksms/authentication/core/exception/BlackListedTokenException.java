package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static com.booksms.authentication.core.exception.Error.TOKEN_IS_BLACKLISTED_EXCEPTION;

public class BlackListedTokenException extends RuntimeException implements CustomException {
   public  BlackListedTokenException(String message) {
       super(message);
   }

    @Override
    public HttpStatus getHttpStatus() {
        return TOKEN_IS_BLACKLISTED_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return TOKEN_IS_BLACKLISTED_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return TOKEN_IS_BLACKLISTED_EXCEPTION.getDescription();
    }
}
