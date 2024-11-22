package com.backend.store.core.domain.exception;

import org.springframework.http.HttpStatus;

public interface CustomException {
    HttpStatus getHttpStatus();
    int getCode();
    String getDescription();
}
