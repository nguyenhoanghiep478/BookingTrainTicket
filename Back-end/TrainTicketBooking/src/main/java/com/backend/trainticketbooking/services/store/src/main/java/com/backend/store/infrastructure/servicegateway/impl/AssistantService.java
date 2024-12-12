package com.backend.store.infrastructure.servicegateway.impl;

import com.backend.store.infrastructure.FeignClient.OpenAIClient;
import com.backend.store.infrastructure.servicegateway.IAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssistantService {
    private final OpenAIClient assistantService;

    public String getResponse(Integer memoryId,String message){
        Map<String, Object> request = new HashMap<>();
        request.put("text", message);

        return assistantService.chat(request);
    }
}
