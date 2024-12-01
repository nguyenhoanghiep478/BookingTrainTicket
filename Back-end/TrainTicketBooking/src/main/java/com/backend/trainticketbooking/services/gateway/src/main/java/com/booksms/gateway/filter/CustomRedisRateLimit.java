package com.booksms.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomRedisRateLimit {
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<Boolean> isAllowed(String key, int maxRequest, Duration window){
        return reactiveRedisTemplate.opsForValue()
                .increment(key)
                .flatMap(currentCount -> {
                    log.info("Current count for key {}: {}", key, currentCount);
                    if(currentCount == 1){
                        reactiveRedisTemplate.expire(key,window).subscribe();
                    }
                    if (currentCount >= maxRequest) {
                        return reactiveRedisTemplate.opsForValue()
                                .set("BLOCKED:" + key, "true", Duration.ofMinutes(10))
                                .then(Mono.just(false));
                    }

                    return Mono.just(currentCount <= maxRequest);
                });
    }
    public Mono<Boolean> isBlocked(String ip) {
        return reactiveRedisTemplate
                .opsForValue()
                .get("BLOCKED:" + ip)
                .flatMap(value -> Mono.just(value != null)); // IP bị chặn nếu tồn tại key "BLOCKED:<IP>"
    }
}
