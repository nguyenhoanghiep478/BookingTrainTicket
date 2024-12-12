package com.backend.store.interfacelayer.service.route;

import com.backend.store.core.domain.entity.schedule.Route;

import java.util.List;

public interface IFindRouteService {
    List<Route> getAll();
}
