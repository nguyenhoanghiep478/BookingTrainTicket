package com.booksms.authentication.core.exception.RestExceptionHandler;

import com.booksms.authentication.core.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

import static com.booksms.authentication.core.exception.Error.*;

@ControllerAdvice
public class GlobalRestException {
    private final String contentType= "application/json";

    @ExceptionHandler(EmailExistedException.class)
    public ResponseEntity<ExceptionDTO> bookNotFoundException(EmailExistedException e) {
        return ResponseEntity
                .status(EMAIL_EXISTED.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                       ExceptionDTO.builder()
                               .code(EMAIL_EXISTED.getCode())
                               .errorDescription(EMAIL_EXISTED.getDescription())
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
    @ExceptionHandler(RegisterFailException.class)
    public ResponseEntity<ExceptionDTO> createFailedException(RegisterFailException e) {
        return ResponseEntity
                .status(CREAT_FAILURE_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(CREAT_FAILURE_EXCEPTION.getCode())
                                .errorDescription(CREAT_FAILURE_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
    @ExceptionHandler(UpdateFailureException.class)
    public ResponseEntity<ExceptionDTO> updateFailedException(UpdateFailureException e) {
        return ResponseEntity
                .status(UPDATE_FAILURE_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(UPDATE_FAILURE_EXCEPTION.getCode())
                                .errorDescription(UPDATE_FAILURE_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
    @ExceptionHandler(MissingArgumentException.class)
    public ResponseEntity<ExceptionDTO> missingArgumentException(UpdateFailureException e) {
        return ResponseEntity
                .status(UPDATE_FAILURE_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(UPDATE_FAILURE_EXCEPTION.getCode())
                                .errorDescription(UPDATE_FAILURE_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionDTO> internalServerException(Exception e) {
//        return ResponseEntity
//                .status(INTERNAL_ERROR.getHttpStatus())
//                .header("Content-Type", contentType)
//                .body(
//                        ExceptionDTO.builder()
//                                .code(INTERNAL_ERROR.getCode())
//                                .errorDescription(INTERNAL_ERROR.getDescription())
//                                .error(e.getMessage())
//                                .build()
//                );
//
//    }

}
