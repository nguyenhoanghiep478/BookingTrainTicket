package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface ICreateTicketService {
    Ticket bookingTicket(CreateTicketRequest request) throws IOException, WriterException;
}
