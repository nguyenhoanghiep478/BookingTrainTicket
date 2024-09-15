package com.bookms.order.core.domain.State;

import lombok.Getter;

@Getter
public enum DateOfWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);


    final int value;
    DateOfWeek(int value) {
        this.value = value;
    }


    public static DateOfWeek fromValue(int value) {
        for (DateOfWeek day : values()) {
            if (day.getValue() == value) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day value: " + value);
    }
}
