package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.exception.ScheduleNotExistException;
import com.backend.store.core.domain.repository.IScheduleRepository;
import com.backend.store.infrastructure.jpaRepository.ScheduleJpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
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

    @Override
    public List<Schedule> findAll() {
        return scheduleJpaRepository.findAll();
    }

    @Override
    public List<Schedule> findBy(List<Criteria> criteria) {
        return abstractSearch(criteria);
    }

    @Override
    public Schedule findById(int id) {
        return scheduleJpaRepository.findById(id).orElseThrow(
                () -> new ScheduleNotExistException(String.format("Schedule with id %s not found", id))
        );
    }

    @Override
    public List<Object[]> findReturnTrip(Integer departureStationId, Integer arrivalStationId, Timestamp arrivalTime) {
        return scheduleJpaRepository.findReturnSchedule(departureStationId,arrivalStationId,arrivalTime);
    }
}
