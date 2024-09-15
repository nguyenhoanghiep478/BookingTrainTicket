package com.booksms.payment.interfaceLayer.service.RedisService;

import com.booksms.payment.interfaceLayer.dto.ResponsePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRedisService {
    private final RedisTemplate<String, ResponsePayment> redisTemplate;

    public void setValue(String paymentId,ResponsePayment responsePayment){
        redisTemplate.opsForValue().set(paymentId,responsePayment);
    }

    public ResponsePayment getValue(String paymentId){
        return  redisTemplate.opsForValue().get(paymentId);
    }

    public void deleteValue(String paymentId){
        redisTemplate.delete(paymentId);
    }
}
