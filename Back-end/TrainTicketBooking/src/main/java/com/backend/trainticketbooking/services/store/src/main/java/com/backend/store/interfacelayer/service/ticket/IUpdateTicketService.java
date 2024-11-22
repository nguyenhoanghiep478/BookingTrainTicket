package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.core.domain.state.TicketStatus;

public interface IUpdateTicketService {

    boolean changeStatus(Integer ticketId, TicketStatus ticketStatus);
}
