package com.backend.store.core.domain.state;

import lombok.Getter;

import java.math.BigDecimal;

import static com.backend.store.core.domain.state.StaticVar.*;

@Getter
public enum SeatClass {
    ECONOMY(BUSINESS_SEAT_PRICE),
    BUSINESS(ECONOMY_SEAT_PRICE),
    NON_BUSINESS(NONE_BUSINESS_SEAT_PRICE);

    final BigDecimal price;
    SeatClass(BigDecimal price) {
        this.price = price;
    }
}
