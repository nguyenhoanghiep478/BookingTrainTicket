package com.backend.store.core.domain.exception.exceptionHandler;

import com.backend.store.core.domain.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.INVALID_ARGUMENT;

@RestControllerAdvice
public class GlobalHandlerException {
    final String contentType = "application/json";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> handleCustomException(RuntimeException e) {
        int code = 500;
        String description = "An unexpected error occurred";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (e instanceof CustomException customException) {
            status = customException.getHttpStatus();
            code = customException.getCode();
            description = customException.getDescription();
        }

        return ResponseEntity
                .status(status)
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(code)
                                .errorDescription(description)
                                .error(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Set<String> validationErrors = new HashSet<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            validationErrors.add(error.getDefaultMessage());
        });

        return ResponseEntity
                .status(INVALID_ARGUMENT.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(INVALID_ARGUMENT.getCode())
                                .errorDescription(INVALID_ARGUMENT.getDescription())
                                .validationErrors(validationErrors)
                                .build()
                )
                ;
    }
}
