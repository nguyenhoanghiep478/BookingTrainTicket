package com.backend.store.interfacelayer.redis.impl;

import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.interfacelayer.dto.objectDTO.HoldingSeatDTO;
import com.backend.store.interfacelayer.dto.objectDTO.SeatDTO;
import com.backend.store.interfacelayer.redis.IRedisHoldSeatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisHoldingSeatService implements IRedisHoldSeatService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public List<HoldingSeatDTO> getALlHoldingSeat(){
       Object object = redisTemplate.opsForValue().get("holdingSeat");
       if(object == null){
           return null;
       }

       if(object instanceof List){
           List<?> list = (List<?>) object;


           return list.stream()
                   .map(item -> objectMapper.convertValue(item, HoldingSeatDTO.class))
                   .collect(Collectors.toList());
       }else{
           throw new IllegalStateException("Data retrieved from Redis is not of type List<HoldingSeat>");
       }

    }
    @Override
    public void add(HoldingSeatDTO seat){
        List<HoldingSeatDTO> seats = getALlHoldingSeat();
        if(seats == null){
            seats = new ArrayList<>();
            seats.add(seat);
        }
        redisTemplate.opsForValue().set("holdingSeat", seats);
    }
    @Override
    public void remove(HoldingSeatDTO seat){
        List<HoldingSeatDTO> seats = getALlHoldingSeat();
        if(seats == null){
            return ;
        }
        seats.remove(seat);
        redisTemplate.opsForValue().set("holdingSeat", seats);
    }

}
