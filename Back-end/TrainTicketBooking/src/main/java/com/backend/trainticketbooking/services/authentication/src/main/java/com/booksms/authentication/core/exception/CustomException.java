package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

public interface CustomException {
    HttpStatus getHttpStatus();
    int getCode();
    String getDescription();
}
