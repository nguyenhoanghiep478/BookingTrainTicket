package com.booksms.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    public NewTopic paymentResponseTopic(){
        return new NewTopic("payment-response", 2, (short) 1);
    }
}
