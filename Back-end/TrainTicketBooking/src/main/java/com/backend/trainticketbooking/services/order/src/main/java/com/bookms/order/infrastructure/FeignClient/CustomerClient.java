package com.bookms.order.infrastructure.FeignClient;

import com.bookms.order.config.FeignConfig;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:5554/api/v1/auth",name = "auth-service",configuration = FeignConfig.class)
public interface CustomerClient {

    @GetMapping("/get-by-id/{id}")
    ResponseEntity<ResponseDTO> getUserById(@PathVariable int id);
}
