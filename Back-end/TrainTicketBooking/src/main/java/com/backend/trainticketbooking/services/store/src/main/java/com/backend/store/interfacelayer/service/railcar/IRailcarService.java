package com.backend.store.interfacelayer.service.railcar;

import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;
import com.backend.store.interfacelayer.dto.response.CreateRailcarResponse;
import com.backend.store.interfacelayer.dto.response.RailcarDTO;

import java.util.List;

public interface IRailcarService {
    CreateRailcarResponse create(CreateRailcarRequest request);

    List<RailcarDTO> getAll();
}
