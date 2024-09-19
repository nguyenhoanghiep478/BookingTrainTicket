package com.backend.store.interfacelayer.service.train.impl;

import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.dto.request.ModifiedRailcarsToTrainRequest;
import com.backend.store.interfacelayer.dto.request.CreateTrainRequest;
import com.backend.store.interfacelayer.dto.response.ModifiedRailcarsToTrainResponse;
import com.backend.store.interfacelayer.dto.response.CreateTrainResponse;
import com.backend.store.interfacelayer.service.train.ICreateTrainService;
import com.backend.store.interfacelayer.service.train.ITrainService;
import com.backend.store.interfacelayer.service.train.IUpdateTrainService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainService implements ITrainService {
    private final ICreateTrainService createTrainService;
    private final IUpdateTrainService updateTrainService;
    private final ModelMapper modelMapper;

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
}
