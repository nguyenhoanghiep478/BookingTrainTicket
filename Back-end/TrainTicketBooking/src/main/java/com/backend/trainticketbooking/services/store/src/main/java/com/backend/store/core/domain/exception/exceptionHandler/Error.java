package com.backend.store.core.domain.exception.exceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    NO_CODE(0,"No code", HttpStatus.INTERNAL_SERVER_ERROR),
    RAILCAR_EXISTED_EXCEPTION(400,"Railcar already existed",HttpStatus.BAD_REQUEST),
    RAILCAR_IN_USE_EXCEPTION(400,"Railcar already in use",HttpStatus.BAD_REQUEST),
    RAILCAR_NOT_EXIST_EXCEPTION(400,"Railcar not exist ",HttpStatus.BAD_REQUEST),
    ROUTE_EXISTED_EXCEPTION(400,"Route already existed ",HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_EXIST_EXCEPTION(400,"Schedule not exist",HttpStatus.BAD_REQUEST),
    SCHEDULE_EXISTED_EXCEPTION(400,"Schedule already existed",HttpStatus.BAD_REQUEST),
    SEAT_IN_USE_EXCEPTION(400,"Seat already existed",HttpStatus.BAD_REQUEST),
    SEAT_NOT_EXIST_EXCEPTION(400,"Seat already existed",HttpStatus.BAD_REQUEST),
    STATION_EXISTED_EXCEPTION(400,"Station already existed",HttpStatus.BAD_REQUEST),
    STATION_NOT_EXISTED_EXCEPTION(400,"Station not exist",HttpStatus.BAD_REQUEST),
    INVALID_STOP_SEQUENCE_EXCEPTION(400,"Invalid stop sequence",HttpStatus.BAD_REQUEST),
    TRAIN_EXISTED_EXCEPTION(400,"Train already existed",HttpStatus.BAD_REQUEST),
    TRAIN_NOT_EXISTED_EXCEPTION(400,"Train not exist",HttpStatus.BAD_REQUEST),
    SCHEDULE_OUT_OF_TIME_EXCEPTION(400,"Schedule out of time",HttpStatus.BAD_REQUEST),
    STATION_NOT_IN_SCHEDULE(400,"Station not in schedule",HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND_EXCEPTION(400,"Customer not found",HttpStatus.BAD_REQUEST),
    TRAIN_NOT_AVAILABLE_EXCEPTION(400,"Train already in use or in a schedule",HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT_OF_SEAT_PER_RAILCAR(400,"Invalid amount of seat per railcar",HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT(400,"Invalid argument",HttpStatus.BAD_REQUEST),
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
