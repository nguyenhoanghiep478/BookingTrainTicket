package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.repository.ITicketRepository;
import com.backend.store.infrastructure.jpaRepository.TicketJpaRepository;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepository extends AbstractRepository<Ticket> implements ITicketRepository {
    private final TicketJpaRepository ticketJpaRepository;
    TicketRepository(Class<Ticket> entityClass, TicketJpaRepository ticketJpaRepository) {
        super(entityClass);
        this.ticketJpaRepository = ticketJpaRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketJpaRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return ticketJpaRepository.findAll();
    }

    @Override
    public List<Ticket> findBy(List<Criteria> criteria) {
        return abstractSearch(criteria);
    }

    @Override
    public List<Ticket> findByScheduleId(Schedule schedule) {
        return ticketJpaRepository.findBySchedule(schedule);
    }

    @Override
    public List<Object[]> findTicketBy1Hour() {
        return ticketJpaRepository.findTicketByOneHour();
    }
}
