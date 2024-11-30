package com.booksms.payment.interfaceLayer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MomoPaymentResponse {
    private String requestId;
    private String orderId;
    private String message;
    private Integer resultCode;
    private String payUrl;
}
