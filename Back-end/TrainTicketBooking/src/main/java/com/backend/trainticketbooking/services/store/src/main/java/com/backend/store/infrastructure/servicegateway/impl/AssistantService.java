package com.backend.store.infrastructure.servicegateway.impl;

import com.backend.store.infrastructure.servicegateway.IAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssistantService {
    private final IAssistantService assistantService;

    public String getResponse(Integer memoryId,String message){
        return assistantService.chat(memoryId,message);
    }
}
