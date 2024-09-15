package com.bookms.order.interfaceLayer.service.redis;

import com.bookms.order.application.model.OrdersModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRedisService {
    private final RedisTemplate<Long, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveOrder(OrdersModel ordersModel) {
        redisTemplate.opsForValue().set(ordersModel.getOrderNumber(),ordersModel);
    }
    public OrdersModel getOrder(Long orderNumber) {
        var result = redisTemplate.opsForValue().get(orderNumber);
        return objectMapper.convertValue(result, OrdersModel.class);
    }
    public void removeOrder(Long orderNumber) {
        redisTemplate.delete(orderNumber);
    }
}
