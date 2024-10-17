package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleStationJpaRepository extends JpaRepository<ScheduleStation, Integer> {
}
