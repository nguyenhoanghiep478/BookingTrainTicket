package com.backend.store.interfacelayer.service.station.impl;

import com.backend.store.application.model.StationModel;
import com.backend.store.application.usecase.Station.CreateStationUseCase;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.interfacelayer.dto.request.CreateStationRequest;
import com.backend.store.interfacelayer.service.station.ICreateStationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStationService implements ICreateStationService {
    private final CreateStationUseCase createStationUseCase;
    private final ModelMapper modelMapper;

    @Override
    public Station create(CreateStationRequest request) {
        StationModel model = modelMapper.map(request, StationModel.class);
        return createStationUseCase.execute(model);
    }
}
