package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Station;

import java.util.List;
import java.util.Optional;

public interface IStationRepository {
    Optional<Station> findByName(String name);

    Station save(Station newStation);

    List<Station> findBy(List<Criteria> criteria);

    List<Station> findAll();
}
