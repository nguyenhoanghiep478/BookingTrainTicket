package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.application.model.TicketModel;
import com.backend.store.application.usecase.Ticket.CreateTicketUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.backend.store.interfacelayer.service.ticket.ICreateTicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTicketService implements ICreateTicketService {
    private final CreateTicketUseCase createTicketUseCase;
    private final ModelMapper modelMapper;


    @Override
    public Ticket bookingTicket(CreateTicketRequest request) {
        TicketModel model =  modelMapper.map(request, TicketModel.class);
        return createTicketUseCase.execute(model);
    }
}
