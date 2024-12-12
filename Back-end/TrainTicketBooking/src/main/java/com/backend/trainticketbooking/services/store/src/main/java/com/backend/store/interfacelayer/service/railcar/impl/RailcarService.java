package com.backend.store.interfacelayer.service.railcar.impl;

import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;
import com.backend.store.interfacelayer.dto.response.CreateRailcarResponse;
import com.backend.store.interfacelayer.dto.response.RailcarDTO;
import com.backend.store.interfacelayer.service.railcar.ICreateRailcarService;
import com.backend.store.interfacelayer.service.railcar.IFindRailcarService;
import com.backend.store.interfacelayer.service.railcar.IRailcarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RailcarService implements IRailcarService {
    private final ICreateRailcarService createRailcarService;
    private final IFindRailcarService findRailcarService;
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

    @Override
    public List<RailcarDTO> getAll() {
        List<Railcar> entities = findRailcarService.getAll();
        return entities.stream().map(this::toDTO).toList();
    }

    private RailcarDTO toDTO(Railcar railcar) {
        String trainName =null;
        if(railcar.getTrain() != null){
                trainName = railcar.getTrain().getTrainName();
        }
        return RailcarDTO.builder()
                .id(railcar.getId())
                .railcarType(railcar.getRailcarType())
                .trainName(trainName)
                .name(railcar.getName())
                .capacity(railcar.getCapacity())
                .isHaveFloor(railcar.getIsHaveFloor())
                .seatPerRow(railcar.getSeatPerRow())
                .build();
    }
}
