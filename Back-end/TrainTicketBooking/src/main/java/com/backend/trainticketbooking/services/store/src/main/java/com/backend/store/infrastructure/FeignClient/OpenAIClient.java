package com.backend.store.infrastructure.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "openai", url = "${open-ai.url}")
public interface OpenAIClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> callOpenAI(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, Object> requestBody
    );
}
