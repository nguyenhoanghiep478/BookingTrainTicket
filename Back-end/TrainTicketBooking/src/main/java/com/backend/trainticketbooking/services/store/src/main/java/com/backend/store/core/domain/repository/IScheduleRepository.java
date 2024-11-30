package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Schedule;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface IScheduleRepository {
    Optional<Schedule> findByRouteIdAndTrainId(Integer routeId, Integer routeId1);

    Schedule save(Schedule newSchedule);

    List<Schedule> findAll();

    List<Schedule> findBy(List<Criteria> criteria);

   Schedule findById(int id);

    List<Object[]> findScheduleByDepartAndArrival(Integer departureStationId, Integer arrivalStationId, Timestamp arrivalTime);
    List<Object[]> findScheduleByDepartAndArrivalName(String departureStationId, String arrivalStationId, Timestamp arrivalTime);

    List<Schedule> findAllAvailable();
}
