package com.backend.store.core.domain.repository;

import com.backend.store.core.domain.entity.schedule.Schedule;

import java.util.Optional;

public interface IScheduleRepository {
    Optional<Schedule> findByRouteIdAndTrainId(Integer routeId, Integer routeId1);

    Schedule save(Schedule newSchedule);
}
