package com.booksms.payment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public JsonMessageConverter jsonMessageConverter(){
        return new JsonMessageConverter();
    }

    @Bean
    public AdminClient adminClient(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        return AdminClient.create(props);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
