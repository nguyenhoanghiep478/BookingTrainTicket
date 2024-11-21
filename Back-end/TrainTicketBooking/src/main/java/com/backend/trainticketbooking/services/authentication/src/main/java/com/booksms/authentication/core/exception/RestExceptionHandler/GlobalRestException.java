package com.booksms.authentication.core.exception.RestExceptionHandler;

import com.booksms.authentication.core.constant.STATIC_VAR;
import com.booksms.authentication.core.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;
import java.util.HashSet;
import java.util.Set;

import static com.booksms.authentication.core.exception.Error.*;

@ControllerAdvice
public class GlobalRestException {
    private final String contentType= "application/json";


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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDTO> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(401)
                                .errorDescription("Invalid username or password") // Thông báo lỗi tùy chỉnh
                                .error(e.getMessage())
                                .build()
                );
    }


    @ExceptionHandler({AccessDeniedException.class, SignatureException.class})
    public ResponseEntity<ProblemDetail> handleSecurityException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ProblemDetail errorDetail;
        HttpStatus status;
        String accessDeniedReason;
        if (ex instanceof AccessDeniedException) {
            status = HttpStatus.UNAUTHORIZED;
            accessDeniedReason = ex.getMessage();
        } else if (ex instanceof SignatureException) {
            status = HttpStatus.FORBIDDEN;
            accessDeniedReason = STATIC_VAR.SIGNATURE_EXCEPTION_MESSAGE;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            accessDeniedReason = STATIC_VAR.INTERNAL_SERVER_ERROR_MESSAGE;
        }

        errorDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        errorDetail.setProperty(STATIC_VAR.ACCESS_DENIED_PROPERTY_REASON, accessDeniedReason);
        return ResponseEntity.status(status).header(response.getHeader("New-access-token")).body(errorDetail);
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
