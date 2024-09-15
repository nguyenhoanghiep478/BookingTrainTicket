package com.booksms.marketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MarketingApplication {
    public static void main(String[] args) {
            SpringApplication.run(MarketingApplication.class, args);
    }

}
