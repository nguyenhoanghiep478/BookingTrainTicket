package com.backend.store.interfacelayer.service.seat;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.interfacelayer.dto.objectDTO.RailcarDTO;

import java.util.List;

public interface IFindSeatService {
    List<AvailableSeatNumberForSchedule> findTotalAvailableSeatAtSchedule(Integer id);

    List<Seat> findAvailableSeatInIds(List<Integer> ids,Integer scheduleId,Integer departureStationId,Integer arrivalStationId);

    List<RailcarDTO> getListRailcarWithSeatAvailableIn(Integer departureStationId, Integer arrivalStationId, Integer scheduleId);
}
