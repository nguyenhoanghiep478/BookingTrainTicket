package com.backend.store.core.domain.exception;

import com.backend.store.core.domain.exception.exceptionHandler.Error;
import org.springframework.http.HttpStatus;

import static com.backend.store.core.domain.exception.exceptionHandler.Error.INVALID_AMOUNT_OF_SEAT_PER_RAILCAR;

public class InvalidAmountOfSeatPerRailcar extends RuntimeException implements CustomException{
    public InvalidAmountOfSeatPerRailcar(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return INVALID_AMOUNT_OF_SEAT_PER_RAILCAR.getHttpStatus();
    }

    @Override
    public int getCode() {
        return INVALID_AMOUNT_OF_SEAT_PER_RAILCAR.getCode();
    }

    @Override
    public String getDescription() {
        return INVALID_AMOUNT_OF_SEAT_PER_RAILCAR.getDescription();
    }
}
