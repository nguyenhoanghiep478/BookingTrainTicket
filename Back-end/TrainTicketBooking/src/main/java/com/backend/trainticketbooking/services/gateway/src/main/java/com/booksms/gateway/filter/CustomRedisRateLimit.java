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
        log.info("Checking rate limit for IP: " + key);
        return reactiveRedisTemplate.opsForValue()
                .increment(key)
                .flatMap(currentCount -> {
                    log.info("Current count for key {}: {}", key, currentCount);
                    if(currentCount == 1){
                        reactiveRedisTemplate.expire(key,window).subscribe();
                    }
                    if (currentCount >= maxRequest) {
                        return reactiveRedisTemplate.opsForValue()
                                .set("PREVENTED:" + key, "true", Duration.ofMinutes(1))
                                .then(Mono.just(false));
                    }

                    return Mono.just(currentCount <= maxRequest);
                });
    }
    public Mono<Boolean> isBlocked(String ip) {
        log.info("redis get blocked ip: {}", ip);
        log.info(reactiveRedisTemplate.opsForValue().get("PREVENTED:" + ip).toString());
        return reactiveRedisTemplate
                .opsForValue()
                .get("PREVENTED:" + ip)
                .doOnNext(value -> log.info("Redis returned value for key PREVENTED:{} -> {}", ip, value))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No value found in Redis for key PREVENTED:{} -> IP not blocked.", ip);
                    return Mono.just(null); // Trả về null để xử lý tiếp trong pipeline
                }))
                .flatMap(value -> {
                    log.info("Blocked status for IP {}: {}", ip, value);
                    return Mono.just(value != null);
                })
                .onErrorResume(error -> {
                    log.error("Fallback logic for IP {}: {}", ip, error.getMessage());
                    return Mono.just(false);
                });
    }
}
