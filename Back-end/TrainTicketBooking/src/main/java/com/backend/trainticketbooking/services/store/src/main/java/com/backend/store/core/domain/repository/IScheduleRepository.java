package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Schedule;

import java.util.List;
import java.util.Optional;

public interface IScheduleRepository {
    Optional<Schedule> findByRouteIdAndTrainId(Integer routeId, Integer routeId1);

    Schedule save(Schedule newSchedule);

    List<Schedule> findAll();

    List<Schedule> findBy(List<Criteria> criteria);

   Schedule findById(int id);
}
