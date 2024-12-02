package com.backend.store.infrastructure.servicegateway.impl;


import com.backend.store.config.OpenAIConfig;
import com.backend.store.infrastructure.FeignClient.OpenAIClient;
import com.backend.store.infrastructure.servicegateway.IChatBotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ContentType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiService implements IChatBotService {

        private final OpenAIClient openAIClient;


        private final OpenAIConfig config;

        public String analyzeMessage(String message) {
//                Map<String, Object> payload = new HashMap<>();
//                payload.put("model", "gpt-4");
//                payload.put("messages", new Object[]{
//                        Map.of("role", "system", "content", "Bạn là AI giúp phân tích tin nhắn để trích xuất thông tin: Ga đi, Ga đến, Thời gian."),
//                        Map.of("role", "user", "content", message)
//                });
//
//                // Gọi API qua Feign Client
//                Map<String, Object> response = openAIClient.callOpenAI(
//                        "Bearer " + config.getApiKey(),
//                        payload
//                );
//
//                // Trích xuất kết quả từ phản hồi
//                Map<String, String> choice = (Map<String, String>) ((Map<String, Object>) ((java.util.List<Object>) response.get("choices")).get(0)).get("message");
//                return choice.get("content");
                return null;
        }
}
