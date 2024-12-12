package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
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

    @Query(value = """
        SELECT ticket.*
        FROM ticket
                 INNER JOIN public.schedule s
                            ON ticket.schedule_id = s.id
                 INNER JOIN public.schedule_station ss
                            ON s.id = ss.schedule_id
                                AND (ticket.departure_station_id IS NULL OR ticket.departure_station_id = ss.station_id)
        WHERE (ticket.arrival_station_id IS NULL OR ticket.arrival_station_id = :arrivalStationId)
          AND (ticket.departure_station_id IS NULL OR ticket.departure_station_id = :departureStationId)
          AND (ss.departure_time IS NULL OR ss.departure_time < :currentTime)
        """, nativeQuery = true)
    List<Ticket> findTickets(
            @Param("departureStationId") Integer departureStationId,
            @Param("arrivalStationId") Integer arrivalStationId,
            @Param("currentTime") Timestamp currentTime
    );

    @Query(
            value = """
        WITH hours AS (
            SELECT generate_series(0, 23) AS hour
        ),
        hourly_intervals AS (
            SELECT
                make_timestamp(:year, :month, :day, hour, 0, 0) AS hour
            FROM hours
        )
        SELECT
            to_char(hi.hour, 'HH24:00') AS hour_label,
            COALESCE(SUM(t.total_price), 0) AS revenue,
            COUNT(t.id) AS ticket_count
        FROM
            hourly_intervals hi
        LEFT JOIN
            ticket t
        ON
            DATE_TRUNC('hour', t.created_date) = hi.hour
        WHERE
            hi.hour::date = make_date(:year, :month, :day)
        GROUP BY
            hi.hour
        ORDER BY
            hi.hour
        """,
            nativeQuery = true
    )
    List<Object[]> findHourlyRevenueByDate(
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day
    );

    @Query(
            value = """
        WITH days AS (
            SELECT generate_series(
                make_date(:year, :month, 1), -- Ngày đầu tiên của tháng
                make_date(:year, :month, 1) + interval '1 month' - interval '1 day', -- Ngày cuối cùng của tháng
                '1 day' -- Tăng dần theo ngày
            ) AS day
        )
        SELECT
            to_char(d.day, 'YYYY-MM-DD') AS day_label, -- Nhãn ngày
            COALESCE(SUM(t.total_price), 0) AS revenue,-- Tổng doanh thu
            COUNT(t.id) AS ticket_count
        FROM
            days d
        LEFT JOIN
            ticket t
        ON
            t.created_date >= d.day
            AND t.created_date < d.day + interval '1 day'
        GROUP BY
            d.day
        ORDER BY
            d.day
        """,
            nativeQuery = true
    )
    List<Object[]> findDailyRevenueByMonth(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query(
            value = """
        WITH months AS (
            SELECT generate_series(
                make_date(:year, 1, 1), -- Ngày đầu tiên của năm
                make_date(:year, 12, 1), -- Ngày đầu tiên của tháng cuối cùng trong năm
                '1 month' -- Tăng dần theo tháng
            ) AS month_start
        )
        SELECT
            to_char(m.month_start, 'YYYY-MM') AS month_label, -- Nhãn tháng (YYYY-MM)
            COALESCE(SUM(t.total_price), 0) AS revenue, -- Tổng doanh thu
            COUNT(t.id) AS ticket_count
        FROM
            months m
        LEFT JOIN
            ticket t
        ON
            t.created_date >= m.month_start
            AND t.created_date < m.month_start + interval '1 month'
        GROUP BY
            m.month_start
        ORDER BY
            m.month_start
        """,
            nativeQuery = true
    )
    List<Object[]> findMonthlyRevenueByYear(
            @Param("year") int year
    );
}
