package com.backend.store.interfacelayer.service.seat;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;

import java.util.List;

public interface IFindSeatService {
    List<AvailableSeatNumberForSchedule> findTotalAvailableSeatAtSchedule(Integer id);
}
