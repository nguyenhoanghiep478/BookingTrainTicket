package com.booksms.gateway.exception;

import org.springframework.web.client.HttpClientErrorException;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
