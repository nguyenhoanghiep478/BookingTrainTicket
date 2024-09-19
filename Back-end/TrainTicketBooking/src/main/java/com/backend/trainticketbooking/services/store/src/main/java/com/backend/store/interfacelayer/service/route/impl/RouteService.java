package com.backend.store.interfacelayer.service.route.impl;

import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.entity.schedule.RouteStation;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.dto.response.CreateRouteResponse;
import com.backend.store.interfacelayer.service.route.ICreateRouteService;
import com.backend.store.interfacelayer.service.route.IRouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteService implements IRouteService {
    private final ICreateRouteService createRouteService;
    private final ModelMapper modelMapper;

    @Override
    public CreateRouteResponse create(CreateRouteRequest request) {
        Route route = createRouteService.create(request);
        return CreateRouteResponse.builder()
                .id(route.getId())
                .name(route.getName())
                .stationName(
                        route.getRouteStations()
                                .stream().
                                map(routeStation -> routeStation.getStation().getName()).toList()
                )
                .build();
    }
}
