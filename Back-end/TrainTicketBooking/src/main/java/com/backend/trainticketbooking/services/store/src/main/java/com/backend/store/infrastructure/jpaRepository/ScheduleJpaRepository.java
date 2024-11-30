package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Integer> {
    Optional<Schedule> findByRouteIdAndTrainId(Integer routeId, Integer trainId);
    @Query(value = "SELECT sch.id AS schedule_id, " +
            "ss_dep.departure_time AS departure_time, " +
            "ss_arr.arrival_time AS arrival_time " +
            "FROM schedule sch " +
            "JOIN schedule_station ss_dep ON sch.id = ss_dep.schedule_id " +
            "JOIN station dep_station ON dep_station.id = ss_dep.station_id " +
            "JOIN schedule_station ss_arr ON sch.id = ss_arr.schedule_id " +
            "JOIN station arr_station ON arr_station.id = ss_arr.station_id " +
            "WHERE dep_station.id = :departureStationId " +
            "AND arr_station.id = :arrivalStationId " +
            "AND ss_dep.departure_time >= (:arrivalTime AT TIME ZONE 'Asia/Ho_Chi_Minh')+ INTERVAL '4 hour' " +
            "ORDER BY ss_dep.departure_time ASC", nativeQuery = true)
    List<Object[]> findScheduleByDepartAndArrival(
            @Param("departureStationId") Integer departureStationId,
            @Param("arrivalStationId") Integer arrivalStationId,
            @Param("arrivalTime") Timestamp arrivalTime);

    @Query(value = "SELECT sch.id AS schedule_id, " +
            "ss_dep.departure_time AS departure_time, " +
            "ss_arr.arrival_time AS arrival_time " +
            "FROM schedule sch " +
            "JOIN schedule_station ss_dep ON sch.id = ss_dep.schedule_id " +
            "JOIN station dep_station ON dep_station.id = ss_dep.station_id " +
            "JOIN schedule_station ss_arr ON sch.id = ss_arr.schedule_id " +
            "JOIN station arr_station ON arr_station.id = ss_arr.station_id " +
            "WHERE dep_station.name = :departureStation " +
            "AND arr_station.name = :arrivalStation " +
            "AND ss_dep.departure_time >= (:arrivalTime AT TIME ZONE 'Asia/Ho_Chi_Minh')+ INTERVAL '4 hour' " +
            "ORDER BY ss_dep.departure_time ASC", nativeQuery = true)
    List<Object[]> findScheduleByDepartAndArrivalName(
            @Param("departureStation") String departureStation,
            @Param("arrivalStation") String arrivalStation,
            @Param("arrivalTime") Timestamp arrivalTime);

    @Query(value = "Select s.* From " +
            "Schedule as s " +
            "join schedule_station as ss on ss.schedule_id = s.id and ss.stop_sequence = 1 " +
            "where ss.departure_time > current_timestamp ",nativeQuery = true
    )
    List<Schedule> findAllAvailable();

}
