package com.backend.store.interfacelayer.service.railcar;

import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;
import com.backend.store.interfacelayer.dto.response.CreateRailcarResponse;

public interface IRailcarService {
    CreateRailcarResponse create(CreateRailcarRequest request);
}
