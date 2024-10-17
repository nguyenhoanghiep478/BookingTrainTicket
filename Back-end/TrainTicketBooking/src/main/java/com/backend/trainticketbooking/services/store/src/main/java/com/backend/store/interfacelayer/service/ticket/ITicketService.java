package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;

import java.util.List;

public interface ITicketService {
    TicketDTO bookingTicket(CreateTicketRequest request);

    List<TicketDTO> getAll();

}
