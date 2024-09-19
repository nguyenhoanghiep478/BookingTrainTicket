package com.backend.store.interfacelayer.service.station;

import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.interfacelayer.dto.request.CreateStationRequest;

public interface ICreateStationService {
    Station create(CreateStationRequest request);
}
