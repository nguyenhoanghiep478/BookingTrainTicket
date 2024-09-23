package com.backend.store.interfacelayer.service.schedule.impl;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.dto.response.CreateScheduleResponse;
import com.backend.store.interfacelayer.service.schedule.ICreateScheduleService;
import com.backend.store.interfacelayer.service.schedule.IFindScheduleService;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import com.backend.store.interfacelayer.service.seat.IFindSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
    private final ICreateScheduleService createScheduleService;
    private final IFindScheduleService findScheduleService;
    private final IFindSeatService findSeatService;

    @Override
    public CreateScheduleResponse create(CreateScheduleRequest request) {
        Schedule schedule = createScheduleService.create(request);
        return CreateScheduleResponse.builder()
                .routeName(schedule.getRoute().getName())
                .trainName(schedule.getRoute().getName())
                .build();
    }

    @Override
    public List<ScheduleDTO> findScheduleById(Integer id) {
        Schedule schedule = findScheduleService.findById(id);
        List<AvailableSeatNumberForSchedule> totalAvailableSeat = findSeatService.findTotalAvailableSeatAtSchedule(id);
        return toSchedulesDTO(schedule,totalAvailableSeat);
    }

    @Override
    public ScheduleDTO toShortScheduleDTO(Schedule schedule) {
        ScheduleStation departureScheduleStation = schedule.getScheduleStations().get(0);
        ScheduleStation arrivalScheduleStation = schedule.getScheduleStations().get(schedule.getScheduleStations().size() - 1);
        return ScheduleDTO.builder()
                .departureStationName(departureScheduleStation.getStation().getName())
                .arrivalStationName(arrivalScheduleStation.getStation().getName())
                .arrivalTime(arrivalScheduleStation.getArrivalTime())
                .departureTime(departureScheduleStation.getDepartureTime())
                .build();
    }


    private List<ScheduleDTO> toSchedulesDTO(Schedule schedule,List<AvailableSeatNumberForSchedule> totalAvailableSeat) {
        List<ScheduleDTO> response = new ArrayList<>();
        String trainName = schedule.getTrain().getTrainName();
        AtomicInteger index = new AtomicInteger(0);
        for(int i= 0 ;i < schedule.getScheduleStations().size() - 1;i++) {
            ScheduleStation departureScheduleStation = schedule.getScheduleStations().get(i);
            ScheduleStation arrivalScheduleStation = schedule.getScheduleStations().get(i+1);
            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                    .departureStationName(departureScheduleStation.getStation().getName())
                    .arrivalStationName(arrivalScheduleStation.getStation().getName())
                    .departureTime(departureScheduleStation.getDepartureTime())
                    .arrivalTime(arrivalScheduleStation.getArrivalTime())
                    .trainName(trainName)
                    .seatNumbersAvailable(totalAvailableSeat.get(index.getAndIncrement()).getSeatNumbers())
                    .build();
            response.add(scheduleDTO);
        }
        return response;
    }
}
