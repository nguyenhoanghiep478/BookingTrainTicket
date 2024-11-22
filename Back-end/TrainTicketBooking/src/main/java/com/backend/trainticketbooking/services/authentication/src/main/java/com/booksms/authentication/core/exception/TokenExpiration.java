package com.booksms.authentication.core.exception;

import com.booksms.authentication.core.constant.STATIC_VAR;
import org.springframework.http.HttpStatus;

public class TokenExpiration extends RuntimeException implements CustomException{
    public TokenExpiration(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return Error.TOKEN_EXPIRATION_EXCEPTION.httpStatus;
    }

    @Override
    public int getCode() {
        return Error.TOKEN_EXPIRATION_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return Error.TOKEN_EXPIRATION_EXCEPTION.getDescription();
    }
}
