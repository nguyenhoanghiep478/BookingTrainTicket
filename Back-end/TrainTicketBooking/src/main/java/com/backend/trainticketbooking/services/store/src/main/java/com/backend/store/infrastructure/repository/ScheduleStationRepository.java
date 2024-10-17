package com.backend.store.infrastructure.repository;

import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.repository.IScheduleStationRepository;
import com.backend.store.infrastructure.jpaRepository.ScheduleStationJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleStationRepository extends AbstractRepository<ScheduleStation> implements IScheduleStationRepository {
    private final ScheduleStationJpaRepository scheduleStationJpaRepository;
    ScheduleStationRepository(Class<ScheduleStation> entityClass, ScheduleStationJpaRepository scheduleStationJpaRepository) {
        super(entityClass);
        this.scheduleStationJpaRepository = scheduleStationJpaRepository;
    }

    @Override
    public void save(ScheduleStation scheduleStation) {
        scheduleStationJpaRepository.save(scheduleStation);
    }
}
