package com.booksms.payment.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private String paymentId;
    private Integer id;
    private String paymentMethod;
    private Long orderNumber;
    private Double totalAmount;
    private Status status;
    @JsonIgnore
    private Timestamp createdAt;
    @JsonIgnore
    private Timestamp lastModifiedAt;
}
