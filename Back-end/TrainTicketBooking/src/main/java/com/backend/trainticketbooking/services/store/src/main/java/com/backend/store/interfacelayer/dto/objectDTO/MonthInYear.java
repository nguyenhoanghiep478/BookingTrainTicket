package com.backend.store.interfacelayer.dto.objectDTO;

public enum MonthInYear {
    JANUARY(1, "January"),
    FEBRUARY(2, "February"),
    MARCH(3, "March"),
    APRIL(4, "April"),
    MAY(5, "May"),
    JUNE(6, "June"),
    JULY(7, "July"),
    AUGUST(8, "August"),
    SEPTEMBER(9, "September"),
    OCTOBER(10, "October"),
    NOVEMBER(11, "November"),
    DECEMBER(12, "December");

    private final int monthNumber;
    private final String displayName;

    MonthInYear(int monthNumber, String displayName) {
        this.monthNumber = monthNumber;
        this.displayName = displayName;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MonthInYear fromMonthNumber(int monthNumber) {
        for (MonthInYear month : values()) {
            if (month.getMonthNumber() == monthNumber) {
                return month;
            }
        }
        throw new IllegalArgumentException("Invalid month number: " + monthNumber);
    }

    public static MonthInYear fromDisplayName(String displayName) {
        for (MonthInYear month : values()) {
            if (month.getDisplayName().equalsIgnoreCase(displayName)) {
                return month;
            }
        }
        throw new IllegalArgumentException("Invalid month display name: " + displayName);
    }
}
