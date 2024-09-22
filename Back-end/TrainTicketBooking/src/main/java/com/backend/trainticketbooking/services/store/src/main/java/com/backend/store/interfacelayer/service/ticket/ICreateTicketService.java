package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;

public interface ICreateTicketService {
    Ticket bookingTicket(CreateTicketRequest request);
}
