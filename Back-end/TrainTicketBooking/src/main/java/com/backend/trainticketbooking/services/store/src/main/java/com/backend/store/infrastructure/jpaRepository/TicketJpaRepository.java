package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.Booking.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<Ticket, Integer> {
}
