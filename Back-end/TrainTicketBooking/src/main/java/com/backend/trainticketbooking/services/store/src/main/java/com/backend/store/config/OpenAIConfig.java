package com.backend.store.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class OpenAIConfig {
    @Value("${open-ai.key}")
    private String apiKey;

    @Value("${open-ai.url}")
    private String apiUrl;
}
