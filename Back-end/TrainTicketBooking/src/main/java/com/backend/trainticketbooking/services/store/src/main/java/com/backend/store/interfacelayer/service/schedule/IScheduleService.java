package com.backend.store.interfacelayer.service.schedule;

import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.dto.response.CreateScheduleResponse;

import java.util.List;

public interface IScheduleService {
    CreateScheduleResponse create(CreateScheduleRequest request);

    List<ScheduleDTO> findScheduleById(Integer id);


    ScheduleDTO toShortScheduleDTO(Schedule schedule);

    List<ScheduleDTO> findRoundTrip(Integer departureStationId, Integer arrivalStationId,Integer scheduleId);
}
