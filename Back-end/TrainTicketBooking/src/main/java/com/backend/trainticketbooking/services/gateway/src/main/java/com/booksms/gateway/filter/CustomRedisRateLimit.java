package com.booksms.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CustomRedisRateLimit {
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<Boolean> isAllowed(String key, int maxRequest, Duration window){
        return reactiveRedisTemplate.opsForValue()
                .increment(key)
                .flatMap(currentCount -> {
                    if(currentCount == 1){
                        reactiveRedisTemplate.expire(key,window).subscribe();
                    }
                    return Mono.just(true);
                });
    }
}
