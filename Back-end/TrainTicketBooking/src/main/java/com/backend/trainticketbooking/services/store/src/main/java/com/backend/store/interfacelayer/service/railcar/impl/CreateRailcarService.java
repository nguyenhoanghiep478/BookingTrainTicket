package com.backend.store.interfacelayer.service.railcar.impl;

import com.backend.store.application.model.RailcarModel;
import com.backend.store.application.usecase.Railcar.CreateRailcarUseCase;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;
import com.backend.store.interfacelayer.service.railcar.ICreateRailcarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRailcarService implements ICreateRailcarService {
    private final CreateRailcarUseCase createRailcarUseCase;
    private final ModelMapper modelMapper;

    @Override
    public Railcar createRaicar(CreateRailcarRequest request) {
        RailcarModel railcarModel = modelMapper.map(request, RailcarModel.class);
        return createRailcarUseCase.execute(railcarModel);
    }
}
