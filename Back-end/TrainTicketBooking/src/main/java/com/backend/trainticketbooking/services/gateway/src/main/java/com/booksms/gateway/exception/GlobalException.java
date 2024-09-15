package com.booksms.gateway.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.booksms.gateway.exception.Error.UNAUTHORIZED_EXCEPTION;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDTO> bookNotFoundException(UnauthorizedException e) {
        String contentType = "application/json";
        return ResponseEntity
                .status(UNAUTHORIZED_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(UNAUTHORIZED_EXCEPTION.getCode())
                                .errorDescription(UNAUTHORIZED_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
}
