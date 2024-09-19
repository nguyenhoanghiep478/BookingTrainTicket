package com.backend.store.interfacelayer.service.route.impl;

import com.backend.store.application.model.RouteModel;
import com.backend.store.application.usecase.Route.CreateRouteUseCase;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.service.route.ICreateRouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRouteService implements ICreateRouteService {
    private final CreateRouteUseCase createRouteUseCase;
    private final ModelMapper modelMapper;

    @Override
    public Route create(CreateRouteRequest request) {
        RouteModel routeModel = modelMapper.map(request, RouteModel.class);
        return createRouteUseCase.execute(routeModel);
    }
}
