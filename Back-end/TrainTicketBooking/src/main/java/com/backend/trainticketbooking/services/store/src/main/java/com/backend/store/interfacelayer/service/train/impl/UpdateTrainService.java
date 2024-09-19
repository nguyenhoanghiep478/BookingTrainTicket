package com.backend.store.interfacelayer.service.train.impl;

import com.backend.store.application.model.TrainModel;
import com.backend.store.application.usecase.Train.ModifiedRailcarUseCase;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.state.ModifiedType;
import com.backend.store.interfacelayer.dto.request.ModifiedRailcarsToTrainRequest;
import com.backend.store.interfacelayer.service.train.IUpdateTrainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UpdateTrainService implements IUpdateTrainService {
    private final ModifiedRailcarUseCase modifiedRailcarUseCase;
    private final ModelMapper modelMapper;


    @Override
    public Train addRailcarsToTrain(ModifiedRailcarsToTrainRequest request) {
        TrainModel trainModel = modelMapper.map(request, TrainModel.class);
        return modifiedRailcarUseCase.execute(trainModel, ModifiedType.ADD);
    }

    @Override
    public Train detachRailcars(ModifiedRailcarsToTrainRequest request) {
        TrainModel trainModel = modelMapper.map(request, TrainModel.class);
        return modifiedRailcarUseCase.execute(trainModel, ModifiedType.DETACH);
    }
}
