package com.booksms.payment.interfaceLayer.service.RedisService;

import com.booksms.payment.application.model.PaymentModel;
import com.booksms.payment.core.domain.entity.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaypalRedisService {
    private final RedisTemplate<Long, Object> redisPaymentModel;
    private final ObjectMapper objectMapper;

    public void setPayment(Long orderNumber,PaymentModel payment) {
        redisPaymentModel.opsForValue().set(orderNumber,payment);
    }

    public PaymentModel getPayment(Long orderNumber) {
        var payment = redisPaymentModel.opsForValue().get(orderNumber);
        return objectMapper.convertValue(payment,PaymentModel.class);
    }

    public void removePayment(Long orderNumber) {
        redisPaymentModel.delete(orderNumber);
    }
}
