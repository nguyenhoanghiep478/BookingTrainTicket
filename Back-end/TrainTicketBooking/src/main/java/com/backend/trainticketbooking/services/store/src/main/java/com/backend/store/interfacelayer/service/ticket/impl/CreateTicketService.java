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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTicketService implements ICreateTicketService {
    private final CreateTicketUseCase createTicketUseCase;
    private final ModelMapper modelMapper;
    private final IAuthenticationService authenticationService;

    @Override
    @KafkaListener(id = "consumer-store-order-created",topics = "order-created")
    public Ticket bookingTicket(CreateTicketRequest request) {
        String customerName;
        String customerEmail;
        log.info(request.toString());
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
                .id(request.getId())
                .status(TicketStatus.IN_PROGRESS)
                .scheduleId(request.getScheduleId())
                .seatIds(request.getSeatIds())
                .price(request.getPrice())
                .build();
        return createTicketUseCase.execute(model);
    }
}
