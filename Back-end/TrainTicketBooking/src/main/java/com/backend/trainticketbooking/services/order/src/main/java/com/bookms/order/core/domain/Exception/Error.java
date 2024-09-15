package com.bookms.order.core.domain.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    NO_CODE(0,"No code", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_NOT_FOUND_EXCEPTION(400,"Book not found",HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND_EXCEPTION(401,"Category not found exception",HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(402,"Category existed",HttpStatus.BAD_REQUEST),
    ORDER_EXISTED(403,"Order already existed",HttpStatus.BAD_REQUEST),
    INSUFFICIENT_QUANTITY_EXCEPTION(403,"Insufficient quantity",HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT(500,"Invalid argument",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(500,"Internal error,please contact admin",HttpStatus.INTERNAL_SERVER_ERROR),;
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
