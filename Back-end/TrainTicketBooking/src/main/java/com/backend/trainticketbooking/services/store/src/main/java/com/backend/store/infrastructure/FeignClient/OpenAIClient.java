package com.backend.store.infrastructure.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "chatbot", url = "http://localhost:5000")
public interface OpenAIClient {

    @PostMapping("/chat")
    String chat(
            @RequestBody Map<String, Object> requestBody
    );
}
