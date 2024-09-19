package com.backend.store.interfacelayer.service.train.impl;

import com.backend.store.application.model.TrainModel;
import com.backend.store.application.usecase.Train.CreateTrainUseCase;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.dto.request.CreateTrainRequest;
import com.backend.store.interfacelayer.service.train.ICreateTrainService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTrainService implements ICreateTrainService {
    private final CreateTrainUseCase createTrainUseCase;
    private final ModelMapper modelMapper;


    @Override
    public Train create(CreateTrainRequest request) {
        TrainModel trainModel = modelMapper.map(request, TrainModel.class);
        return createTrainUseCase.execute(trainModel);
    }
}
