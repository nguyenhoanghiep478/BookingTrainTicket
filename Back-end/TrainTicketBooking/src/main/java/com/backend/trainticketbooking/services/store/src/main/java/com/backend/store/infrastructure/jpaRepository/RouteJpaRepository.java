package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.schedule.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteJpaRepository extends JpaRepository<Route, Integer> {
    Optional<Route> findByName(String name);
}
