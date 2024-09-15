package com.booksms.payment.core.domain.StaticVar;

public class StaticVar {
    public static final String SUCCESS_URL = "http://localhost:5558/api/v1/payment/paypal/success?orderNumber=";
    public static final String FAILURE_URL = "http://localhost:5558/api/v1/payment/paypal/cancel?orderNumber=";
}
