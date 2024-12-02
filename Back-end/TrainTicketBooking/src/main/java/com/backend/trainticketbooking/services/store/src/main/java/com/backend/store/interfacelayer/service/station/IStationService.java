package com.backend.store.interfacelayer.service.station;

import com.backend.store.interfacelayer.dto.objectDTO.StationDTO;
import com.backend.store.interfacelayer.dto.request.CreateStationRequest;
import com.backend.store.interfacelayer.dto.response.CreateStationResponse;

import java.util.List;

public interface IStationService {
    CreateStationResponse create(CreateStationRequest station);
    boolean isValidStation(String stationName);
    List<StationDTO> getAll();
}
