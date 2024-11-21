package com.backend.store.interfacelayer.service.schedule;

import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.dto.response.CreateScheduleResponse;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface IScheduleService {
    CreateScheduleResponse create(CreateScheduleRequest request);

    List<ScheduleDTO> findScheduleById(Integer id);


    ScheduleDTO toShortScheduleDTO(Schedule schedule);

    List<ScheduleDTO> findRoundTrip(Integer departureStationId, Integer arrivalStationId,Integer scheduleId);

    List<ScheduleDTO> findScheduleByDepartAndArrival(Integer departureStationId, Integer arrivalStationId);

    List<ScheduleDTO> findScheduleByDepartAndArrivalAndDepartureTime(Integer departureStationId, Integer arrivalStationId, Timestamp departureTime);

    List<ScheduleDTO> findScheduleByDepartAndArrivalNameAndDepartureTime(String departureStationId, String arrivalStationId, Timestamp sqlTimestamp);
}
