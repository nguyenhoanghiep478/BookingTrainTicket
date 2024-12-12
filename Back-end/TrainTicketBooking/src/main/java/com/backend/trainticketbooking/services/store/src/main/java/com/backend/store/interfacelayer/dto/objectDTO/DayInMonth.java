package com.backend.store.interfacelayer.dto.objectDTO;

public enum DayInMonth {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELVE(12),
    THIRTEEN(13),
    FOURTEEN(14),
    FIFTEEN(15),
    SIXTEEN(16),
    SEVENTEEN(17),
    EIGHTEEN(18),
    NINETEEN(19),
    TWENTY(20),
    TWENTY_ONE(21),
    TWENTY_TWO(22),
    TWENTY_THREE(23),
    TWENTY_FOUR(24),
    TWENTY_FIVE(25),
    TWENTY_SIX(26),
    TWENTY_SEVEN(27),
    TWENTY_EIGHT(28),
    TWENTY_NINE(29),
    THIRTY(30),
    THIRTY_ONE(31);

    private final int day;

    DayInMonth(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public static DayInMonth fromDay(int day) {
        for (DayInMonth d : values()) {
            if (d.getDay() == day) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid day: " + day);
    }
}
