package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.core.constant.STATIC_VAR;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<Integer, Object> redisTemplate;
    private final RedisTemplate<String,Integer> sessionStorage;
    private final RedisTemplate<String,Object> userSessionStorage;
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

    public void addSession(){
        if(Boolean.FALSE.equals(sessionStorage.hasKey(STATIC_VAR.SESSION_STORAGE))){
            sessionStorage.opsForValue().set(STATIC_VAR.SESSION_STORAGE,1);
            return;
        }
        int currentTotalSession = countSession();
        sessionStorage.opsForValue().set(STATIC_VAR.SESSION_STORAGE,currentTotalSession + 1);
    }

    public void removeSession(){
        if(Boolean.FALSE.equals(sessionStorage.hasKey(STATIC_VAR.SESSION_STORAGE))){
            return;
        }
        int currentTotalSession = countSession();
        sessionStorage.opsForValue().set(STATIC_VAR.SESSION_STORAGE,currentTotalSession - 1);
    }

    public Integer countSession(){
        Object object = sessionStorage.opsForValue().get(STATIC_VAR.SESSION_STORAGE);
        return Integer.parseInt(String.valueOf(object));
    }


    public void addUserSession(String userName, String accessToken) {
        userSessionStorage.opsForValue().set(userName, accessToken);
        addSession();
    }

    public boolean isUserWorking(String userName) {
        return Boolean.TRUE.equals(userSessionStorage.hasKey(userName));
    }

    public void removeUserSession(String userName){
        userSessionStorage.delete(userName);
        removeSession();
    }

}
