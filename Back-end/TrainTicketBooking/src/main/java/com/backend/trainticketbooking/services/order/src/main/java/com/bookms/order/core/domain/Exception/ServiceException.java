package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import java.util.Set;

public class ServiceException extends RuntimeException implements CustomException{
    private final HttpStatus status;
    private final String error;
    private final Set<String> validationErrors;
    private final Integer errorCode;
    public ServiceException(String message, HttpStatus status,Integer errorCode ,String error, Set<String> validationErrors) {
        this.status = status;
        this.error = error;
        this.validationErrors = validationErrors;
        this.errorCode = errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }

    @Override
    public int getCode() {
        return this.errorCode;
    }

    @Override
    public String getDescription() {
        return this.error;
    }
}
