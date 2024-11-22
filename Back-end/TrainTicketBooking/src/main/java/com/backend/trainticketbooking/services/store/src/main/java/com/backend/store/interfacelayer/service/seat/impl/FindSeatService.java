package com.backend.store.interfacelayer.service.seat.impl;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;
import com.backend.store.application.usecase.Schedule.FindScheduleUseCase;
import com.backend.store.application.usecase.Seat.FindSeatUseCase;
import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.interfacelayer.dto.objectDTO.RailcarDTO;
import com.backend.store.interfacelayer.service.seat.IFindSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindSeatService implements IFindSeatService {
    private final FindSeatUseCase findSeatUseCase;
    private final FindScheduleUseCase findScheduleUseCase;
    private final FindStationUseCase findStationUseCase;
    @Override
    public List<AvailableSeatNumberForSchedule> findTotalAvailableSeatAtSchedule(Integer id) {
        return findSeatUseCase.findTotalAvailableSeatsAtSchedule(id);
    }

    @Override
    public List<Seat> findAvailableSeatInIds(List<Integer> ids,Integer scheduleId,Integer departureStationId,Integer arrivalStationId) {
        findScheduleUseCase.validate(findScheduleUseCase.findById(scheduleId),findStationUseCase.getStationById(departureStationId) );
        return findSeatUseCase.findInIdsAndAvailableSeatsAtStation(ids, scheduleId, departureStationId,arrivalStationId);
    }

    @Override
    public List<RailcarDTO> getListRailcarWithSeatAvailableIn(Integer departureStationId, Integer arrivalStationId, Integer scheduleId) {
        return findSeatUseCase.findSeatAvailableGroupByRailcarAt(departureStationId,arrivalStationId,scheduleId);
    }
}
