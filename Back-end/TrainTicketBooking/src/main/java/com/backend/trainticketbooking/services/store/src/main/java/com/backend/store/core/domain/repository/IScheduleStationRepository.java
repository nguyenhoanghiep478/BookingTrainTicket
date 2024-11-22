package com.backend.store.core.domain.repository;

import com.backend.store.core.domain.entity.schedule.ScheduleStation;

public interface IScheduleStationRepository {
    void save(ScheduleStation scheduleStation);
}
