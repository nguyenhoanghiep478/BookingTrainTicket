package com.backend.store.core.domain.state;

import java.math.BigDecimal;

public class StaticVar {
    static BigDecimal ECONOMY_SEAT_PRICE = BigDecimal.valueOf(200000);
    static BigDecimal BUSINESS_SEAT_PRICE = BigDecimal.valueOf(500000);
    static BigDecimal NONE_BUSINESS_SEAT_PRICE = BigDecimal.ZERO;
}
