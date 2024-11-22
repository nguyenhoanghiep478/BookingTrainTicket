package com.backend.store.interfacelayer.service.train.impl;

import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TrainDTO;
import com.backend.store.interfacelayer.dto.request.ModifiedRailcarsToTrainRequest;
import com.backend.store.interfacelayer.dto.request.CreateTrainRequest;
import com.backend.store.interfacelayer.dto.response.ModifiedRailcarsToTrainResponse;
import com.backend.store.interfacelayer.dto.response.CreateTrainResponse;
import com.backend.store.interfacelayer.service.schedule.IFindScheduleService;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import com.backend.store.interfacelayer.service.train.ICreateTrainService;
import com.backend.store.interfacelayer.service.train.IFindTrainService;
import com.backend.store.interfacelayer.service.train.ITrainService;
import com.backend.store.interfacelayer.service.train.IUpdateTrainService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService implements ITrainService {
    private final ICreateTrainService createTrainService;
    private final IUpdateTrainService updateTrainService;
    private final ModelMapper modelMapper;
    private final IFindTrainService findTrainService;
    private final IScheduleService scheduleService;

    @Override
    public CreateTrainResponse create(CreateTrainRequest request) {
        Train train = createTrainService.create(request);
        return modelMapper.map(train, CreateTrainResponse.class);
    }

    @Override
    public ModifiedRailcarsToTrainResponse addRailcars(ModifiedRailcarsToTrainRequest request) {
        Train train = updateTrainService.addRailcarsToTrain(request);

        return modelMapper.map(train, ModifiedRailcarsToTrainResponse.class);
    }

    @Override
    public ModifiedRailcarsToTrainResponse detachRailcars(ModifiedRailcarsToTrainRequest request) {
        Train train = updateTrainService.detachRailcars(request);
        return modelMapper.map(train, ModifiedRailcarsToTrainResponse.class);
    }

    @Override
    public List<TrainDTO> getAll() {
        List<Train> trains = findTrainService.getAll();
        return trains.stream().map(this::toDTO).toList();
    }

    private TrainDTO toDTO(Train train) {
        List<Schedule> schedules = train.getSchedules();

        List<ScheduleDTO> scheduleDTOs = schedules.stream().map(
                scheduleService::toShortScheduleDTO
        ).toList();

        return TrainDTO.builder()
                .id(train.getId())
                .trainNumber(train.getTrainNumber())
                .trainName(train.getTrainName())
                .trainType(train.getTrainType())
                .capacity(train.getCapacity())
                .totalRailCars(train.getTotalRailCars())
                .schedules(scheduleDTOs)
                .trainStatus(train.getTrainStatus())
                .currentStation(train.getCurrentStation().getName())
                .build();
    }
}
