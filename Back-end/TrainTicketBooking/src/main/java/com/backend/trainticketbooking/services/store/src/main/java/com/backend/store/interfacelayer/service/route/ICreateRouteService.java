package com.backend.store.interfacelayer.service.route;

import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;

public interface ICreateRouteService {
    Route create(CreateRouteRequest request);
}
