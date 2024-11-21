package com.booksms.authentication.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    NO_CODE(0,"No code", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_EXISTED(402,"Email existed",HttpStatus.BAD_REQUEST),
    CREAT_FAILURE_EXCEPTION(500,"Create failure",HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_FAILURE_EXCEPTION(501,"Update failure",HttpStatus.INTERNAL_SERVER_ERROR),
    MISSING_ARGUMENT_EXCEPTION(502,"Missing argument",HttpStatus.BAD_REQUEST),
    USER_BLOCKED_EXCEPTION(423,"User blocked",HttpStatus.LOCKED),
    USER_NOT_VERIFIED_EXCEPTION(403,"User unverified",HttpStatus.FORBIDDEN),
    TOKEN_IS_BLACKLISTED_EXCEPTION(403,"Token is in blacklist",HttpStatus.FORBIDDEN),
    INVALID_SESSION_LOGIN_EXCEPTION(403,"This account logged in other place",HttpStatus.UNAUTHORIZED),
    INVALID_ARGUMENT(500,"Invalid argument",HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRATION_EXCEPTION(401,"token expried",HttpStatus.UNAUTHORIZED),
    INTERNAL_ERROR(500,"Internal error,please contact admin",HttpStatus.INTERNAL_SERVER_ERROR);
    ;


    final int code;
    final String description;
    final HttpStatus httpStatus;
    Error(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
