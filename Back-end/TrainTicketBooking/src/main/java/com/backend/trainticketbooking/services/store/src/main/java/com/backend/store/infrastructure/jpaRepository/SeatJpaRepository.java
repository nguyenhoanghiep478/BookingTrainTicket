package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.train.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SeatJpaRepository extends JpaRepository<Seat, Integer> {
    @Query(value = "SELECT s.id AS seat_id " +
            "FROM seat s " +
            "JOIN railcar r ON s.railcar_id = r.id " +
            "JOIN train t ON r.train_id = t.id " +
            "JOIN schedule sch ON sch.train_id = t.id " +
            "JOIN schedule_station dep_ss ON dep_ss.schedule_id = sch.id " +
            "JOIN schedule_station arr_ss ON arr_ss.schedule_id = sch.id " +
            "LEFT JOIN ticket_seat ts ON s.id = ts.seat_id " +
            "LEFT JOIN ticket tkt ON ts.ticket_id = tkt.id " +
            "WHERE sch.id = :scheduleId " +
            "AND dep_ss.station_id = :departureStationId " +
            "AND arr_ss.station_id = :arrivalStationId " +
            "AND dep_ss.stop_sequence < arr_ss.stop_sequence " +
            "AND (ts.ticket_id IS NULL OR s.is_available = TRUE " +
            "OR (tkt.departure_station_id NOT BETWEEN dep_ss.station_id AND arr_ss.station_id " +
            "AND tkt.arrival_station_id NOT BETWEEN dep_ss.station_id AND arr_ss.station_id)) " +
            "AND s.id in (:seatIds)"+
            "ORDER BY s.seat_number",
            nativeQuery = true)
    List<Integer>  findAvailableSeatIds(@Param("scheduleId") Integer scheduleId,
                                    @Param("departureStationId") Integer departure_station_id,
                                    @Param("arrivalStationId") Integer arrival_station_id,
                                    @Param("seatIds") List<Integer> seatIds);

    @Query(value = "SELECT " +
            "ss.id AS scheduleStationId, " +
            "r.name, "+
            "STRING_AGG(s.id::text,',' ORDER BY  s.seat_number) as seat_id, " +
            "STRING_AGG(s.seat_number::text, ',' ORDER BY s.seat_number) AS seat_numbers " +
            "FROM schedule_station ss " +
            "JOIN schedule sch ON ss.schedule_id = sch.id " +
            "JOIN railcar r ON r.train_id = sch.train_id " +
            "JOIN seat s ON s.railcar_id = r.id " +
            "LEFT JOIN ticket_seat ts ON ts.seat_id = s.id " +
            "LEFT JOIN ticket t ON ts.ticket_id = t.id " +
            "WHERE ss.schedule_id = :scheduleId " +
            "AND (t.id IS NULL OR (t.arrival_station_id <= ss.station_id OR t.departure_station_id > ss.station_id)) " +
            "GROUP BY ss.id,r.name " +
            "ORDER BY ss.id",
            nativeQuery = true)
    List<Object[]> countAvailableSeats(@Param("scheduleId") Integer scheduleId);

    @Query(value = "SELECT r.name AS railcar_name, " +
            "STRING_AGG(s.id::text, ',' ORDER BY s.seat_number) AS seat_id, " +
            "STRING_AGG(s.seat_number, ',' ORDER BY s.seat_number) AS available_seats " +
            "FROM seat s " +
            "JOIN railcar r ON s.railcar_id = r.id " +
            "JOIN train t ON r.train_id = t.id " +
            "JOIN schedule sch ON sch.train_id = t.id " +
            "JOIN schedule_station dep_ss ON dep_ss.schedule_id = sch.id " +
            "JOIN schedule_station arr_ss ON arr_ss.schedule_id = sch.id " +
            "LEFT JOIN ticket_seat ts ON s.id = ts.seat_id " +
            "LEFT JOIN ticket tkt ON ts.ticket_id = tkt.id " +
            "WHERE sch.id = :scheduleId " +
            "AND dep_ss.station_id = :departureStationId " +
            "AND arr_ss.station_id = :arrivalStationId " +
            "AND dep_ss.stop_sequence < arr_ss.stop_sequence " +
            "AND (ts.ticket_id IS NULL OR s.is_available = TRUE " +
            "AND (tkt.departure_station_id NOT BETWEEN dep_ss.station_id AND arr_ss.station_id " +
            "AND tkt.arrival_station_id NOT BETWEEN dep_ss.station_id AND arr_ss.station_id)) " +
            "GROUP BY r.name " +
            "ORDER BY r.name",
            nativeQuery = true)
    List<Object[]> findAvailableSeatsGroupByRailcar(@Param("scheduleId") Integer scheduleId,
                                                 @Param("departureStationId") Integer departureStationId,
                                                 @Param("arrivalStationId") Integer arrivalStationId);



}
