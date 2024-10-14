package com.backend.store.application.usecase.Ticket;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.repository.ITicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindTicketUseCase {
    private final ITicketRepository ticketRepository;

    public List<Ticket> execute(List<Criteria> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return ticketRepository.findAll();
        }
        return ticketRepository.findBy(criteria);
    }
    public List<Ticket> findByScheduleId(Schedule schedule) {

        return ticketRepository.findByScheduleId(schedule);
    }
}
