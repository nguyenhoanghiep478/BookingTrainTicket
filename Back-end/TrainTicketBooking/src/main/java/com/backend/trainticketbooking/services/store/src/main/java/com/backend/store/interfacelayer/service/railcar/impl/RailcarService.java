package com.backend.store.interfacelayer.service.railcar.impl;

import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;
import com.backend.store.interfacelayer.dto.response.CreateRailcarResponse;
import com.backend.store.interfacelayer.service.railcar.ICreateRailcarService;
import com.backend.store.interfacelayer.service.railcar.IRailcarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RailcarService implements IRailcarService {
    private final ICreateRailcarService createRailcarService;
    private final ModelMapper modelMapper;

    @Override
    public CreateRailcarResponse create(CreateRailcarRequest request) {
        Railcar railcar = createRailcarService.createRaicar(request);
        CreateRailcarResponse response =  modelMapper.map(railcar, CreateRailcarResponse.class);
        if(railcar.getTrain() != null){
            response.setTrainName(railcar.getTrain().getTrainName());
        }
        return response;
    }
}
