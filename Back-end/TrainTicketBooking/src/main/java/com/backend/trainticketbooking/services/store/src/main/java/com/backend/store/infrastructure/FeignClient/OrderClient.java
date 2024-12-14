package com.backend.store.infrastructure.FeignClient;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service",url = "http://localhost:5557/api/v1/order")
public interface OrderClient {
    @GetMapping("/get-by-order-number")
    ResponseEntity<ResponseDTO> getByOrderNumber(@RequestParam Long orderNumber);
}
