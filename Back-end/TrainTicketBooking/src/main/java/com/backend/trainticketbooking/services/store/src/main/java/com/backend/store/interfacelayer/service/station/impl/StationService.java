package com.backend.store.interfacelayer.service.station.impl;

import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.interfacelayer.dto.objectDTO.StationDTO;
import com.backend.store.interfacelayer.dto.request.CreateStationRequest;
import com.backend.store.interfacelayer.dto.response.CreateStationResponse;
import com.backend.store.interfacelayer.service.station.ICreateStationService;
import com.backend.store.interfacelayer.service.station.IFindStationService;
import com.backend.store.interfacelayer.service.station.IStationService;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService implements IStationService {
    private final ICreateStationService createStationService;
    private final ModelMapper modelMapper;
    private final IFindStationService findStationService;
    private final FindStationUseCase findStationUseCase;

    @Override

    public CreateStationResponse create(CreateStationRequest request) {
        Station station = createStationService.create(request);
        return modelMapper.map(station, CreateStationResponse.class);
    }

    @Override
    public List<StationDTO> getAll() {
        List<Station> entities = findStationService.getAll();
        return entities.stream().map(this::toDTO).toList();
    }


    @Override
    public boolean isValidStation(String stationName){
        return findStationService.getAll().stream().anyMatch(station -> station.getName().equals(stationName));
    }

    private StationDTO toDTO(Station entity) {

        return StationDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress().toAddress())
                .build();
    }
}
