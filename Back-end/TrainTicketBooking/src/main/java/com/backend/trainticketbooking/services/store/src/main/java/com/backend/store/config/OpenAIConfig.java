package com.backend.store.config;

import com.backend.store.infrastructure.servicegateway.IAssistantService;
import com.backend.store.infrastructure.servicegateway.impl.ToolExcutor;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import com.backend.store.interfacelayer.service.station.IStationService;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class OpenAIConfig {
        private final IStationService stationService;
        private final IScheduleService scheduleService;
        @Bean
        public IAssistantService assistantServiceImpl() {
                return AiServices.builder(IAssistantService.class)
                        .chatLanguageModel(chatLanguageModel())
                        .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                        .tools(new ToolExcutor(scheduleService,stationService))
                        .build();
        }
        @Bean
        public ChatLanguageModel chatLanguageModel() {
                return OpenAiChatModel.builder()
                        .apiKey("demo")
                        .modelName("gpt-4o-mini")
                        .build();
        }
}
