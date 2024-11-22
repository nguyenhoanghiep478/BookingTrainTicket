package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.application.model.TicketModel;
import com.backend.store.application.usecase.Ticket.UpdateTicketUseCase;
import com.backend.store.core.domain.state.TicketStatus;
import com.backend.store.interfacelayer.service.ticket.IUpdateTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTicketService implements IUpdateTicketService {

    private final UpdateTicketUseCase updateTicketUseCase;

    @Override
    public boolean changeStatus(Integer ticketId, TicketStatus ticketStatus) {
        TicketModel ticketModel = TicketModel.builder()
                .id(ticketId)
                .status(ticketStatus)
                .build();
       return updateTicketUseCase.execute(ticketModel);

    }
}
