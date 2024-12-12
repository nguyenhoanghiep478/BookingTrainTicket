package com.backend.store.core.domain.state;

import lombok.Getter;

import static com.backend.store.core.domain.state.SeatClass.*;
import static com.backend.store.core.domain.state.SeatType.*;
@Getter
public enum RailcarType {
    PASSENGER_CAR(ECONOMY, SEATING,64),
    SOFT_CAR(ECONOMY,SEATING,64),
    SLEEPER_CAR_4(BUSINESS, SLEEPER,28),
    SLEEPER_CAR_6(BUSINESS, SLEEPER,42),
    DINNING_CAR(NON_BUSINESS, DINNING,64),
    FIRST_CLASS_CAR(BUSINESS,SEATING,64),
    ECONOMY_CAR(ECONOMY, SEATING,64),;

    final SeatClass seatClass;
    final SeatType seatType;
    final int maxCapacity;
    RailcarType(SeatClass seatClass, SeatType seatType, int maxCapacity) {
        this.seatClass = seatClass;
        this.seatType = seatType;
        this.maxCapacity = maxCapacity;
    }
}
