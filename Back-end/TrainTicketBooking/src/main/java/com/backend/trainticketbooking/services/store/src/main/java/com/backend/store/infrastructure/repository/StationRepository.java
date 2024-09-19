package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.repository.IStationRepository;
import com.backend.store.infrastructure.jpaRepository.StationJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StationRepository extends AbstractRepository<Station> implements IStationRepository {
    private final StationJpaRepository stationJpaRepository;
    StationRepository(Class<Station> entityClass, StationJpaRepository stationJpaRepository) {
        super(entityClass);
        this.stationJpaRepository = stationJpaRepository;
    }

    @Override
    public Optional<Station> findByName(String name) {
        return stationJpaRepository.findByName(name);
    }

    @Override
    public Station save(Station newStation) {
        return stationJpaRepository.save(newStation);
    }

    @Override
    public List<Station> findBy(List<Criteria> criteria) {
        return abstractSearch(criteria);
    }

    @Override
    public List<Station> findAll() {
        return stationJpaRepository.findAll();
    }
}
