package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findBySchedule(Schedule schedule);


    @Query(value = "SELECT t.email, ss.departure_time, station.name, t.customer_name, train.train_name, seat.seat_number " +
            "FROM Ticket AS t " +
            "INNER JOIN ticket_seat ts ON t.id = ts.ticket_id " +
            "INNER JOIN Seat seat ON ts.seat_id = seat.id " +
            "INNER JOIN station station ON station.id = t.departure_station_id " +
            "INNER JOIN Schedule s ON s.id = t.schedule_id " +
            "INNER JOIN Train train ON s.train_id = train.id " +
            "INNER JOIN schedule_station ss ON s.id = ss.schedule_id " +
            "WHERE t.departure_station_id = ss.station_id " +
            "AND t.status = 'IN_PROGRESS' " +
            "AND ss.departure_time >= (CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Ho_Chi_Minh') " +
            "AND ss.departure_time < (CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Ho_Chi_Minh') + INTERVAL '1 hour'",nativeQuery = true)
    List<Object[]> findTicketByOneHour();


}
