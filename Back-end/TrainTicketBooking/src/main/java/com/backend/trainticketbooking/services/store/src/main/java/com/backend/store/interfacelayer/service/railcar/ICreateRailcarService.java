package com.backend.store.interfacelayer.service.railcar;

import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;

public interface ICreateRailcarService {
    Railcar createRaicar(CreateRailcarRequest request);
}
