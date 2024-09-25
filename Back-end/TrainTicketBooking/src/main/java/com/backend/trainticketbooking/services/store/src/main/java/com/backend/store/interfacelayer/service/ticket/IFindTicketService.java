package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.core.domain.entity.Booking.Ticket;

import java.util.List;

public interface IFindTicketService {
    List<Ticket> getAll();
}
