package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;

import java.util.List;

public interface ITicketRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAll();

    List<Ticket> findBy(List<Criteria> criteria);

    List<Ticket> findByScheduleId(Schedule schedule);

    List<Object[]> findTicketBy1Hour();
}
