package com.backend.store.interfacelayer.service.route.impl;

import com.backend.store.application.usecase.Route.FindRouteUseCase;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.interfacelayer.dto.objectDTO.RouteDTO;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.dto.response.CreateRouteResponse;
import com.backend.store.interfacelayer.service.route.IFindRouteService;
import com.backend.store.interfacelayer.service.route.IRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindRouteService implements IFindRouteService {
    private final FindRouteUseCase findRouteUseCase;

    @Override
    public List<Route> getAll() {
        return findRouteUseCase.execute(null);
    }
}
