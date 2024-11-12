package com.backend.store.interfacelayer.service.seat.impl;

import com.backend.store.application.usecase.Seat.UpdateSeatUseCase;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.interfacelayer.service.seat.IUpdateSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateSeatService implements IUpdateSeatService {
    private final UpdateSeatUseCase updateSeatUseCase;


    @Override
    public void holdSeat(List<Seat> seats) {
        updateSeatUseCase.holdSeat(seats);
    }


}
