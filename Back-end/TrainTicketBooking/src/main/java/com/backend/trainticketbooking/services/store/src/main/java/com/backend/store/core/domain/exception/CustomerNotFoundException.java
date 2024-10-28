package com.backend.store.core.domain.exception;

import com.backend.store.core.domain.exception.exceptionHandler.Error;
import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.CUSTOMER_NOT_FOUND_EXCEPTION;

public class CustomerNotFoundException extends RuntimeException implements CustomException {
    public CustomerNotFoundException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return CUSTOMER_NOT_FOUND_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return CUSTOMER_NOT_FOUND_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return CUSTOMER_NOT_FOUND_EXCEPTION.getDescription();
    }
}
