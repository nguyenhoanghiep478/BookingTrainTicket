package com.booksms.authentication.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic1() {
        return new NewTopic("UserRegister", 2, (short) 1);
    }

    @Bean
    public NewTopic resetPasswordTopic() {
        return new NewTopic("ResetPassword", 2, (short) 1);
    }

    @Bean
    public NewTopic response() {
        return new NewTopic("UserRegisterResponse", 2, (short) 1);
    }

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }
}
