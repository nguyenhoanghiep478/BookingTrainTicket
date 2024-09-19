package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.schedule.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationJpaRepository extends JpaRepository<Station, Integer> {
    Optional<Station> findByName(String name);
}
