package com.backend.store.interfacelayer.service.station.impl;

import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.interfacelayer.dto.request.CreateStationRequest;
import com.backend.store.interfacelayer.dto.response.CreateStationResponse;
import com.backend.store.interfacelayer.service.station.ICreateStationService;
import com.backend.store.interfacelayer.service.station.IStationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService implements IStationService {
    private final ICreateStationService createStationService;
    private final ModelMapper modelMapper;

    @Override
    public CreateStationResponse create(CreateStationRequest request) {
        Station station = createStationService.create(request);
        return modelMapper.map(station, CreateStationResponse.class);
    }
}
