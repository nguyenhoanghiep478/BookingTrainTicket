package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.repository.IRouteRepository;
import com.backend.store.infrastructure.jpaRepository.RouteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RootRepository extends AbstractRepository<Route> implements IRouteRepository {
    private final RouteJpaRepository routeJpaRepository;
    RootRepository(Class<Route> entityClass, RouteJpaRepository routeJpaRepository) {
        super(entityClass);
        this.routeJpaRepository = routeJpaRepository;
    }

    @Override
    public Optional<Route> findByName(String name) {
        return routeJpaRepository.findByName(name);
    }

    @Override
    public Route save(Route newRoute) {
        return routeJpaRepository.save(newRoute);
    }

    @Override
    public List<Route> findAll() {
        return routeJpaRepository.findAll();
    }

    @Override
    public List<Route> findBy(List<Criteria> criteria) {
        return abstractSearch(criteria);
    }
}
