package com.backend.store.web.anonymous;

import com.backend.store.infrastructure.servicegateway.IChatBotService;
import com.backend.store.interfacelayer.dto.objectDTO.ChatBotRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/store/anonymous")
@RequiredArgsConstructor
public class ChatBotAnonymousController {
    private final IChatBotService chatBotService;

    @PostMapping("/chat")
    public String analyzeMessage(@RequestBody ChatBotRequest request) throws JsonProcessingException {
        return chatBotService.analyzeMessage(request.getMessage());
    }
}
