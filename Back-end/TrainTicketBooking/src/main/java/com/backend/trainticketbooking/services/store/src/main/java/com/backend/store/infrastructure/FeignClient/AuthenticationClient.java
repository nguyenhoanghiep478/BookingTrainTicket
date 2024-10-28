package com.backend.store.infrastructure.FeignClient;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authentication-service",url = "http://localhost/api/v1/auth")
public interface AuthenticationClient {
    @GetMapping("/get-by-id/{id}")
   ResponseEntity<ResponseDTO> getById(@PathVariable int id);
}
