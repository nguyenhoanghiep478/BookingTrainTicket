package com.backend.store.infrastructure.repository;

import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.repository.IScheduleRepository;
import com.backend.store.infrastructure.jpaRepository.ScheduleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ScheduleRepository extends AbstractRepository<Schedule> implements IScheduleRepository {
    private final ScheduleJpaRepository scheduleJpaRepository;
    ScheduleRepository(Class<Schedule> entityClass, ScheduleJpaRepository scheduleJpaRepository) {
        super(entityClass);
        this.scheduleJpaRepository = scheduleJpaRepository;
    }

    @Override
    public Optional<Schedule> findByRouteIdAndTrainId(Integer routeId, Integer trainId) {
        return scheduleJpaRepository.findByRouteIdAndTrainId(routeId, trainId);
    }

    @Override
    public Schedule save(Schedule newSchedule) {
        return scheduleJpaRepository.save(newSchedule);
    }
}
