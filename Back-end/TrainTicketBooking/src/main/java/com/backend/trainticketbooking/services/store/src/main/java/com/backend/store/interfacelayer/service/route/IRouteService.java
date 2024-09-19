package com.backend.store.interfacelayer.service.route;

import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.dto.response.CreateRouteResponse;

public interface IRouteService {
    CreateRouteResponse create(CreateRouteRequest request);
}
