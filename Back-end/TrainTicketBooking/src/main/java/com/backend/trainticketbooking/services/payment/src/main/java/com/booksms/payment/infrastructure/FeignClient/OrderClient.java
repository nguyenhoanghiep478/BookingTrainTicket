package com.booksms.payment.infrastructure.FeignClient;

import com.booksms.payment.interfaceLayer.dto.ResponsePayment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service",url = "http://localhost:5557/api/v1/order")
public interface OrderClient {
    @PostMapping("/success-payment")
    ResponseEntity<?> callBack( @RequestBody ResponsePayment orderNumber);

    @PostMapping("/cancel-payment")
    ResponseEntity<?> callBackCancel(@RequestBody ResponsePayment orderNumber);

}
