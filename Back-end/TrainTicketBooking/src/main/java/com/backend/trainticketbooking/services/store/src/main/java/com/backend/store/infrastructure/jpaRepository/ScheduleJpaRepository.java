package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findByRouteIdAndTrainId(Integer routeId, Integer trainId);
}
