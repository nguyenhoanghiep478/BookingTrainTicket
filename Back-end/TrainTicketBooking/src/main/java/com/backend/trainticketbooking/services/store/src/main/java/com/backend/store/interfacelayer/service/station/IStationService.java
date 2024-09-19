package com.backend.store.interfacelayer.service.station;

import com.backend.store.interfacelayer.dto.request.CreateStationRequest;
import com.backend.store.interfacelayer.dto.response.CreateStationResponse;

public interface IStationService {
    CreateStationResponse create(CreateStationRequest station);
}
