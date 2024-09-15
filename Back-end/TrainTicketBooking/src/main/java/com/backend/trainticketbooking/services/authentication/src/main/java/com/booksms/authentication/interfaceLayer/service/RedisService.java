package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<Integer, Object> redisTemplate;
    private final ModelMapper modelMapper;

    public void setValue(final Integer userId, final UserDTO userDTO) {
        redisTemplate.opsForValue().set(userId, userDTO);
    }

    public UserDTO getValue(final Integer userId) {
        return modelMapper.map(redisTemplate.opsForValue().get(userId),UserDTO.class);
    }

    public void removeValue(final Integer userId) {
        redisTemplate.delete(userId);
    }
}
