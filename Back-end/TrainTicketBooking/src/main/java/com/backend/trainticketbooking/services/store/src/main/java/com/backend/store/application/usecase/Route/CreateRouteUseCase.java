package com.backend.store.application.usecase.Route;

import com.backend.store.application.model.RouteModel;
import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.schedule.*;
import com.backend.store.core.domain.exception.RouteExistedException;
import com.backend.store.core.domain.repository.IRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class CreateRouteUseCase {
    private final IRouteRepository routeRepository;
    private final FindStationUseCase findStationUseCase;

    public Route execute(RouteModel routeModel) {
        Optional<Route> route = routeRepository.findByName(routeModel.getName());
        if(route.isPresent()) {
            throw new RouteExistedException(String.format("Route with name %s already existed", routeModel.getName()));
        }

        Route newRoute = map(routeModel);

        return routeRepository.save(newRoute);
    }



    private Route map(RouteModel routeModel) {
        Route route = new Route();
        route.setName(routeModel.getName());

        List<RouteStation> routeStations = route.getRouteStations();
        List<Station> stations = findStationUseCase.getListStationInIds(routeModel.getStationIds());
        AtomicInteger index = new AtomicInteger(1);
        stations.forEach(station -> {
            RouteStation routeStation = new RouteStation();
            routeStation.setRoute(route);
            routeStation.setStation(station);
            routeStation.setStopOrder(index.getAndIncrement());
            routeStations.add(routeStation);
        });
        route.setRouteStations(routeStations);

        return route;
    }


}
