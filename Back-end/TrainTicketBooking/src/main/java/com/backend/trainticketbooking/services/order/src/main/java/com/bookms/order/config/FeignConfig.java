package com.bookms.order.config;

import com.bookms.order.core.domain.Exception.CustomerErrorDecoder;
import com.bookms.order.web.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomerErrorDecoder();
    }
}
