package com.bookms.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.converter.JsonMessageConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic orderCreated(){
        return new NewTopic("order-created", 1, (short) 1);
    }

    @Bean
    public NewTopic orderCreatedResponse(){
        return new NewTopic("order-created-response", 1, (short) 1);
    }

    @Bean
    public NewTopic preCreateOder(){
        return new NewTopic("pre-create-order", 1, (short) 1);
    }

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }
}
