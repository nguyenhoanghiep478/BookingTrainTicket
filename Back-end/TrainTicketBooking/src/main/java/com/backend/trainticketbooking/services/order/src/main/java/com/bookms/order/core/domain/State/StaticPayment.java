package com.bookms.order.core.domain.State;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StaticPayment {
    PAYPAL("sale","Paypal","Payment by Paypal"),
    MOMO("sale","MOMO","Payment by Momo");

    public final String intent;
    public final String method;
    public final String description;



}
