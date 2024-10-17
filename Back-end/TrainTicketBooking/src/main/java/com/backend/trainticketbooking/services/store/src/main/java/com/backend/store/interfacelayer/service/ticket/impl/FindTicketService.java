package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.application.usecase.Ticket.FindTicketUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.service.ticket.IFindTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindTicketService implements IFindTicketService {
    private final FindTicketUseCase findTicketUseCase;

    @Override
    public List<Ticket> getAll() {
        return findTicketUseCase.execute(null);
    }


}
