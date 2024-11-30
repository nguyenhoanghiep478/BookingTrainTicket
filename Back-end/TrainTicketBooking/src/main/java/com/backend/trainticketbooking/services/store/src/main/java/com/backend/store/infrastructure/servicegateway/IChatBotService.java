package com.backend.store.infrastructure.servicegateway;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IChatBotService {
    String analyzeMessage(String message) throws JsonProcessingException;
}
