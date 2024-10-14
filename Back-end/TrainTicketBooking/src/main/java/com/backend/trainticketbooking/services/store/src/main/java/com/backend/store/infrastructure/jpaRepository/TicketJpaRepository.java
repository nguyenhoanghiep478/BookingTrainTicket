package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findBySchedule(Schedule schedule);
}
