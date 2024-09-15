package com.bookms.order.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private Long orderNumber;
    private Integer customerId;
    private Double total;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
