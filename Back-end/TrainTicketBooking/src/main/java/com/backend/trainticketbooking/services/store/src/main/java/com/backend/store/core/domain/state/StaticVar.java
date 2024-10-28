package com.backend.store.core.domain.state;

import java.math.BigDecimal;

public class  StaticVar {
   public static final BigDecimal ECONOMY_SEAT_PRICE = BigDecimal.valueOf(200000);
   public static final BigDecimal BUSINESS_SEAT_PRICE = BigDecimal.valueOf(500000);
   public static final BigDecimal NONE_BUSINESS_SEAT_PRICE = BigDecimal.ZERO;
   public static final Long TRAVEL_TIME_MINUTES = 60L;
   public static final Long BREAK_TIME_MINUTES = 30L;
   public static final Long NOTIFICATIONS_DURATION_SECONDS = 60000L;
}
