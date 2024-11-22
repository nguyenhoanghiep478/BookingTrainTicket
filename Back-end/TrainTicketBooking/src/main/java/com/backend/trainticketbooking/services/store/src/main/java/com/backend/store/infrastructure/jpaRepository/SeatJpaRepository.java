package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.train.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<Seat, Integer> {
    @Query(value = "SELECT s.id AS seat_id " +
            "FROM schedule_station ss " +
            "JOIN schedule sch ON ss.schedule_id = sch.id " +
            "JOIN railcar r ON r.train_id = sch.train_id " +
            "JOIN seat s ON s.railcar_id = r.id " +
            "LEFT JOIN ticket t ON sch.id = t.schedule_id " +
            "LEFT JOIN ticket_seat ts ON ts.seat_id = s.id " +
            "WHERE ss.schedule_id = :scheduleId AND ss.station_id = :stationId AND (s.id IN :seatIds) " +
            "GROUP BY s.id, ss.station_id " +
            "HAVING COUNT(t.id) = 0 OR " +
            "(MIN(t.departure_station_id) > ss.station_id OR MAX(t.arrival_station_id) <= ss.station_id)",
            nativeQuery = true)
    List<Integer> findAvailableSeatIds(@Param("scheduleId") Integer scheduleId,
                                    @Param("stationId") Integer stationId,
                                    @Param("seatIds") List<Integer> seatIds);

    @Query(value = "SELECT " +
            "ss.id AS scheduleStationId, " +
            "STRING_AGG(s.seat_number::text, ',' ORDER BY s.seat_number) AS seat_numbers " +
            "FROM schedule_station ss " +
            "JOIN schedule sch ON ss.schedule_id = sch.id " +
            "JOIN railcar r ON r.train_id = sch.train_id " +
            "JOIN seat s ON s.railcar_id = r.id " +
            "LEFT JOIN ticket_seat ts ON ts.seat_id = s.id " +
            "LEFT JOIN ticket t ON ts.ticket_id = t.id " +
            "WHERE ss.schedule_id = :scheduleId " +
            "AND (t.id IS NULL OR (t.arrival_station_id <= ss.station_id OR t.departure_station_id > ss.station_id)) " +
            "GROUP BY ss.id " +
            "ORDER BY ss.id",
            nativeQuery = true)
    List<Object[]> countAvailableSeats(@Param("scheduleId") Integer scheduleId);
}
