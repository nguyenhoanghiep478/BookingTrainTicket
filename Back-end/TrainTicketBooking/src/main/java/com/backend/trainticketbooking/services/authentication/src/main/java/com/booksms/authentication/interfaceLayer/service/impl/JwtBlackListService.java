package com.booksms.authentication.interfaceLayer.service.impl;

import com.booksms.authentication.core.constant.STATIC_VAR;
import com.booksms.authentication.interfaceLayer.service.IJwtBlackListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JwtBlackListService implements IJwtBlackListService {
    private final RedisTemplate<String,String> redisTemplate;

    public JwtBlackListService(@Qualifier("blackList") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean isBlackList(String jwtToken) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(jwtToken));
    }

    @Override
    public void addToBlackList(String jwtToken) {
        redisTemplate.opsForValue().set(jwtToken,"blacklisted", STATIC_VAR.EXPIRATION_JWT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
        String value =  redisTemplate.opsForValue().get(jwtToken);
        if(value == null){
            log.error("faild to add token {} to redis",jwtToken);
        }else{
            log.info("Successfully added token {} to redis",jwtToken);
        }
    }
}
