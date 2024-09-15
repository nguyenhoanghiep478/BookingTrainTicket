package com.booksms.marketing.infrastructure.feignClient;

import com.booksms.marketing.interfaceLayer.dto.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "customer-service",url = "http://localhost:5554/api/v1/auth",configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface CustomerClient {

    @GetMapping("/get-by-id/{id}")
     ResponseEntity<ResponseDTO> getUserById(@PathVariable int id);
}
