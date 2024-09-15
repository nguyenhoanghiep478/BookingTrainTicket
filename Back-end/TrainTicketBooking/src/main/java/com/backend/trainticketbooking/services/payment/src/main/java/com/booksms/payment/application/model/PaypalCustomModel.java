package com.booksms.payment.application.model;

import com.booksms.payment.core.domain.StaticVar.StaticVar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.booksms.payment.core.domain.StaticVar.StaticVar.FAILURE_URL;
import static com.booksms.payment.core.domain.StaticVar.StaticVar.SUCCESS_URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaypalCustomModel {
    Double total;
    String currency;
    String method;
    String intent;
    String description;
    Long orderNumber;
    @JsonIgnore
    String cancelUrl;
    @JsonIgnore
    String returnUrl;

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
        this.cancelUrl = FAILURE_URL + orderNumber;
        this.returnUrl = SUCCESS_URL + orderNumber;
    }
}
