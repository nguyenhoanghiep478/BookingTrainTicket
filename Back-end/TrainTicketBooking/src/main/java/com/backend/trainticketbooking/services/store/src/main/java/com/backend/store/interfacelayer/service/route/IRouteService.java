package com.backend.store.interfacelayer.service.route;

import com.backend.store.interfacelayer.dto.objectDTO.RouteDTO;
import com.backend.store.interfacelayer.dto.objectDTO.RouteDetailDTO;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.dto.response.CreateRouteResponse;

import java.util.List;

public interface IRouteService {
    CreateRouteResponse create(CreateRouteRequest request);

    List<RouteDTO> getAll();
    List<RouteDetailDTO> getAllDetail();
}
