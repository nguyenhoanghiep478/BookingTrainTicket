package com.backend.store.interfacelayer.dto.objectDTO;

public enum Hour {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    ELEVEN,
    TWELVE,
    THIRTEEN,
    FOURTEEN,
    FIFTEEN,
    SIXTEEN,
    SEVENTEEN,
    EIGHTEEN, // Đã sửa chính tả
    NINETEEN,
    TWENTY,
    TWENTY_ONE,
    TWENTY_TWO,
    TWENTY_THREE; //

    public static Hour fromHourLabel(String hourLabel) {
        int hour = Integer.parseInt(hourLabel.split(":")[0]); // Lấy phần giờ từ chuỗi HH24:00
        return Hour.values()[hour];
    }
}
