package com.backend.store.interfacelayer.service.route.impl;

import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.entity.schedule.RouteStation;
import com.backend.store.interfacelayer.dto.objectDTO.RouteDTO;
import com.backend.store.interfacelayer.dto.objectDTO.RouteDetailDTO;
import com.backend.store.interfacelayer.dto.objectDTO.StationDTO;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.dto.response.CreateRouteResponse;
import com.backend.store.interfacelayer.service.route.ICreateRouteService;
import com.backend.store.interfacelayer.service.route.IFindRouteService;
import com.backend.store.interfacelayer.service.route.IRouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService implements IRouteService {
    private final ICreateRouteService createRouteService;
    private final ModelMapper modelMapper;
    private final IFindRouteService findRouteService;
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

    @Override
    public List<RouteDTO> getAll() {
        List<Route> routes = findRouteService.getAll();
        List<RouteDTO> response = new ArrayList<>();
        for (Route route : routes) {
            response.add(toDTO(route));
        }
        return response;
    }

    @Override
    public List<RouteDetailDTO> getAllDetail() {
        List<Route> routes = findRouteService.getAll();
        List<RouteDetailDTO> response = new ArrayList<>();
        for (Route route : routes) {
            response.add(toRouteDetailDTO(route));
        }
        return response;
    }

    private RouteDTO toDTO(Route route) {
        int lastIndex = route.getRouteStations().size()-1;
        return  RouteDTO.builder()
                .id(route.getId())
                .name(route.getName())
                .departureStationName(route.getRouteStations().get(0).getStation().getName())
                .arrivalStationName(route.getRouteStations().get(lastIndex).getStation().getName())
                .build();
    }

    private RouteDetailDTO toRouteDetailDTO(Route route){
        RouteDetailDTO routeDetailDTO = new RouteDetailDTO();
        routeDetailDTO.setId(route.getId());
        routeDetailDTO.setName(route.getName());
        List<StationDTO> stations = new ArrayList<>();
        for(RouteStation routeStation : route.getRouteStations()){
            StationDTO stationDTO = StationDTO.builder()
                    .id(routeStation.getStation().getId())
                    .name(routeStation.getStation().getName())
                    .address(routeStation.getStation().getAddress().toAddress())
                    .stopSequence(routeStation.getStopOrder())
                    .build();
            stations.add(stationDTO);
        }
        routeDetailDTO.setStations(stations);
        return routeDetailDTO;
    }
}
