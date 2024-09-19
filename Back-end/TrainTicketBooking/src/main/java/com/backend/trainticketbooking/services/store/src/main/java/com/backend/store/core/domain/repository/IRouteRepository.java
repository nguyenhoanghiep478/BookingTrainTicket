package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Route;

import java.util.List;
import java.util.Optional;

public interface IRouteRepository {
    Optional<Route> findByName(String name);

    Route save(Route newRoute);

    List<Route> findAll();

    List<Route> findBy(List<Criteria> criteria);
}
