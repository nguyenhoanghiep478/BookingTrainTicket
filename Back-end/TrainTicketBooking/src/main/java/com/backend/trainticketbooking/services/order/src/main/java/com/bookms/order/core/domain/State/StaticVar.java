package com.bookms.order.core.domain.State;

import java.math.BigDecimal;

public class StaticVar {
    public static final BigDecimal ECONOMY_SEAT_PRICE = BigDecimal.valueOf(200000);
    public static final BigDecimal BUSINESS_SEAT_PRICE = BigDecimal.valueOf(500000);
    public static final BigDecimal NONE_BUSINESS_SEAT_PRICE = BigDecimal.ZERO;
    public static Integer AMOUNT_TOP_SALES = 6;
}
