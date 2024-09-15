package com.booksms.marketing.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Dotenv dotenv(){
        return Dotenv.configure().directory("/book micro-service/microservice-books-ecommerce").load();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
