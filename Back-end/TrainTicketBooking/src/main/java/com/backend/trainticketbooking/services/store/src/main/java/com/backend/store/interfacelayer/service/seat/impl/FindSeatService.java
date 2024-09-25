package com.backend.store.interfacelayer.service.seat.impl;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;
import com.backend.store.application.usecase.Seat.FindSeatUseCase;
import com.backend.store.interfacelayer.service.seat.IFindSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindSeatService implements IFindSeatService {
    private final FindSeatUseCase findSeatUseCase;

    @Override
    public List<AvailableSeatNumberForSchedule> findTotalAvailableSeatAtSchedule(Integer id) {
        return findSeatUseCase.findTotalAvailableSeatsAtSchedule(id);
    }
}
