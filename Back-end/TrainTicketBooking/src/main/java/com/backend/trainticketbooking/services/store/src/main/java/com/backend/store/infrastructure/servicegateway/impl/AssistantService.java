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

    public String getResponse(String memoryId,String message){
        Map<String, Object> request = new HashMap<>();
        request.put("message", message);
        request.put("id",memoryId);
        return assistantService.chat(request);
    }
}
