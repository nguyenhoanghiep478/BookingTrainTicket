package com.backend.store.core.domain.state;

import lombok.Getter;

import static com.backend.store.core.domain.state.SeatClass.*;
import static com.backend.store.core.domain.state.SeatType.*;
@Getter
public enum RailcarType {
    PASSENGER_CAR(ECONOMY, SEATING),
    SLEEPER_CAR(BUSINESS, SLEEPER),
    DINNING_CAR(NON_BUSINESS, DINNING),
    FIRST_CLASS_CAR(BUSINESS,SEATING),
    ECONOMY_CAR(ECONOMY, SEATING),;

    final SeatClass seatClass;
    final SeatType seatType;
    RailcarType(SeatClass seatClass,SeatType seatType) {
        this.seatClass = seatClass;
        this.seatType = seatType;
    }
}
