package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.application.model.TicketModel;
import com.backend.store.application.usecase.Ticket.CreateTicketUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.state.TicketStatus;
import com.backend.store.infrastructure.servicegateway.IAuthenticationService;
import com.backend.store.interfacelayer.dto.objectDTO.CustomerDTO;
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
    private final IAuthenticationService authenticationService;

    @Override
    public Ticket bookingTicket(CreateTicketRequest request) {
        String customerName;
        String customerEmail;
        if(request.getCustomerId() != null){
            CustomerDTO customerDTO = authenticationService.getCustomerById(request.getCustomerId());
            customerName = customerDTO.getFirstName() + " " + customerDTO.getLastName();
            customerEmail = customerDTO.getEmail();
        }else{
            customerName = request.getCustomerName();
            customerEmail = request.getCustomerEmail();
        }

        TicketModel model = TicketModel.builder()
                .arrivalStationId(request.getArrivalStationId())
                .departureStationId(request.getDepartureStationId())
                .customerEmail(customerEmail)
                .customerName(customerName)
                .status(TicketStatus.IN_PROGRESS)
                .scheduleId(request.getScheduleId())
                .seatIds(request.getSeatIds())
                .price(request.getPrice())
                .build();
        return createTicketUseCase.execute(model);
    }
}
