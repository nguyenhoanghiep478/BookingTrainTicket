package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

public interface CustomException {
    HttpStatus getHttpStatus();
    int getCode();
    String getDescription();
}
