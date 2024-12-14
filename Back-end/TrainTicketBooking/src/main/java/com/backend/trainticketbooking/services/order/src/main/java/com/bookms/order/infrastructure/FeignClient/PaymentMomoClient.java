package com.bookms.order.infrastructure.FeignClient;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "momoClient",url = "http://localhost:5558/api/v1/payment/momo/anonymous")
public interface PaymentMomoClient {
    @PostMapping("/create")
    ResponseEntity<ResponseDTO> createByMomo(@RequestBody PaymentModel payment);

}
