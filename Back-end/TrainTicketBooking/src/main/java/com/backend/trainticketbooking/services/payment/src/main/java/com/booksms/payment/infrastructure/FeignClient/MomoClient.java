package com.booksms.payment.infrastructure.FeignClient;

import com.booksms.payment.interfaceLayer.dto.MomoPaymentRequest;
import com.booksms.payment.interfaceLayer.dto.MomoPaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "momoClient",url = "${momo.payment-url}")
public interface MomoClient {
    @PostMapping("/create")
    MomoPaymentResponse createPayment(@RequestBody MomoPaymentRequest paymentRequest);
}
