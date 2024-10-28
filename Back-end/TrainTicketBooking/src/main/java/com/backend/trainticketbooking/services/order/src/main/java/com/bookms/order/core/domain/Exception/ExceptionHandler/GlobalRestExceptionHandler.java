package com.bookms.order.core.domain.Exception.ExceptionHandler;

import com.bookms.order.core.domain.Exception.Error;
import com.bookms.order.core.domain.Exception.ExceptionDTO;
import com.bookms.order.core.domain.Exception.InSufficientQuantityException;
import com.bookms.order.core.domain.Exception.InvalidToken;
import com.bookms.order.core.domain.Exception.OrderExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

import static com.bookms.order.core.domain.Exception.Error.*;

@ControllerAdvice
public class GlobalRestExceptionHandler {
    final String contentType = "application/json";


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
    @ExceptionHandler(OrderExistException.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(OrderExistException e) {
        return ResponseEntity
                .status(ORDER_EXISTED.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(ORDER_EXISTED.getCode())
                                .errorDescription(ORDER_EXISTED.getDescription())
                                .build()
                )
                ;
    }
    @ExceptionHandler(InSufficientQuantityException.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(InSufficientQuantityException e) {
        return ResponseEntity
                .status(INSUFFICIENT_QUANTITY_EXCEPTION.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(INSUFFICIENT_QUANTITY_EXCEPTION.getCode())
                                .errorDescription(e.getMessage())
                                .build()
                )
                ;
    }
    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(InvalidToken e) {
        return ResponseEntity.status(INVALID_TOKEN_EXCEPTION.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(INVALID_TOKEN_EXCEPTION.getCode())
                                .errorDescription(e.getMessage())
                                .build()
                );

    }
}
