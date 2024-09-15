package com.booksms.gateway.ServiceGateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service",url = "http://localhost:5554")
public interface AuthClient {

    @GetMapping("/api/v1/auth/validate-token")
    Boolean validateToken(@RequestHeader("Authorization") String token);
}
