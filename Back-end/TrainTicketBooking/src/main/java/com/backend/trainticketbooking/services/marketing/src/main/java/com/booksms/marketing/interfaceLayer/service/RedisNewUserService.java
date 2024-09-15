package com.booksms.marketing.interfaceLayer.service;

import com.booksms.marketing.interfaceLayer.dto.request.NewUserRegister;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Getter
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisNewUserService{
    private final RedisTemplate<Long, Object> redisTemplate;
    private final ObjectMapper mapper;

   public void save(Long token,NewUserRegister newUserRegister){
       log.info("Redis template is {} ",redisTemplate);
       redisTemplate.opsForValue().set(token, newUserRegister);
   }

    public void save(Long token,NewUserRegister newUserRegister,Duration duration){
       redisTemplate.opsForValue().set(token, newUserRegister,duration);
   }



    public NewUserRegister get(Long token){
       var object = redisTemplate.opsForValue().get(token);

       return mapper.convertValue(object, NewUserRegister.class);
    }

   public void delete(Long token){
       redisTemplate.delete(token);
   }
}
