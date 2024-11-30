package com.booksms.payment.application.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MomoModel {
    private String configName = "Momo";
    private String partnerCode;
    private String returnUrl;
    private String IpnUrl;
    private String accessKey;
    private String secretKey;
    private String paymentUrl;
}
