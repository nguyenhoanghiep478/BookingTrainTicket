package com.bookms.order.infrastructure.FeignClient;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "payment-cod-service",url = "http://localhost:5558/api/v1/payment/cod")
public interface PaymentCodClient {
    @PostMapping("/create")
    ResponseEntity<ResponseDTO> createByCod(@RequestBody PaymentModel payment);
}
