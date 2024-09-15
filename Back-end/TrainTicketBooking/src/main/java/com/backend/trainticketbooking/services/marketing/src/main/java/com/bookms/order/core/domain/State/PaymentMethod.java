package com.bookms.order.core.domain.State;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    PAYPAL("PAYPAL"),
    COD("COD"),
    MOMO("MOMO");

    final String value;


    PaymentMethod(String value) {
        this.value = value;
    }

}
