package com.backend.store.interfacelayer.redis;

import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.interfacelayer.dto.objectDTO.HoldingSeatDTO;
import com.backend.store.interfacelayer.dto.objectDTO.SeatDTO;

import java.util.List;

public interface IRedisHoldSeatService {
    List<HoldingSeatDTO> getALlHoldingSeat();
    void add(HoldingSeatDTO seat);
    void remove(HoldingSeatDTO seat);
}
