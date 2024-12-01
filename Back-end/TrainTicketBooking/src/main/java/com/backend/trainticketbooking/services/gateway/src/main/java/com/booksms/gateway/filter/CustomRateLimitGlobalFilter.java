package com.booksms.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
public class CustomRateLimitGlobalFilter  implements GlobalFilter,Ordered  {
    @Autowired
    private CustomRedisRateLimit customRedisRateLimit;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        return customRedisRateLimit.isBlocked(ip)
                .flatMap(isBlocked -> {
                    if (isBlocked) {
                        log.warn("IP " + ip + " is blocked.");
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        return exchange.getResponse().setComplete();
                    }
                    return customRedisRateLimit.isAllowed(ip,10,Duration.ofSeconds(10))
                            .flatMap(isAllowed -> {
                                if(!isAllowed){
                                    String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                                    log.warn("Too many requests from IP: " + ip);
                                    if (token != null) {
                                        log.warn("Sending token to Kafka: " + token);
                                        kafkaTemplate.send("log-out-user", token);
                                    }
                                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                                    return exchange.getResponse().setComplete();
                                }
                                return chain.filter(exchange);
                            });
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
