package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.application.model.TicketModel;
import com.backend.store.application.usecase.Schedule.FindScheduleUseCase;
import com.backend.store.application.usecase.Ticket.CreateTicketUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.state.TicketStatus;
import com.backend.store.infrastructure.servicegateway.IAuthenticationService;
import com.backend.store.interfacelayer.dto.objectDTO.CustomerDTO;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.backend.store.interfacelayer.dto.request.PrintTicketRequest;
import com.backend.store.interfacelayer.service.QRCode.IQRCodeService;
import com.backend.store.interfacelayer.service.ticket.ICreateTicketService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTicketService implements ICreateTicketService {
    private final CreateTicketUseCase createTicketUseCase;
    private final ModelMapper modelMapper;
    private final IAuthenticationService authenticationService;
    private final IQRCodeService qrCodeService;
    private final KafkaTemplate<String,PrintTicketRequest> printTicketKafkaTemplate;
    private final FindScheduleUseCase findScheduleUseCase;

    @Override
    @KafkaListener(id = "consumer-store-order-created",topics = "order-created")
    public Ticket bookingTicket(CreateTicketRequest request) throws IOException, WriterException {
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
                .orderNumber(request.getOrderNumber())
                .build();
        Ticket ticket = createTicketUseCase.execute(model);

        PrintTicketRequest printTicketRequest = toPrintTicketRequest(ticket,request.getOrderNumber());

        printTicketKafkaTemplate.send("printTicket", printTicketRequest);

        return ticket;
    }
    public void sendQrCode(Ticket ticket) throws IOException, WriterException {
        printTicketKafkaTemplate.send("printTicket", toPrintTicketRequest(ticket,1234556L));
    }

    public PrintTicketRequest toPrintTicketRequest(Ticket ticket,Long orderNumber) throws IOException, WriterException {
        Schedule schedule = ticket.getSchedule();
        Timestamp departureTime = schedule.getScheduleStations().stream().filter(st -> st.getStation().getId().equals(ticket.getDepartureStation().getId())).findFirst().get().getDepartureTime();


        PrintTicketRequest printTicketRequest = new PrintTicketRequest();
        printTicketRequest.setQrCode(qrCodeService.generateBase64QRCode(ticket.getId().toString()));
        printTicketRequest.setCustomerName(ticket.getCustomerName());
        printTicketRequest.setEmail(ticket.getEmail());
        printTicketRequest.setDepartureTime(departureTime);
        printTicketRequest.setOrderNumber(orderNumber);
        printTicketRequest.setSeatName(ticket.getTicketSeats()
                .stream()
                .map(ticketSeat -> ticketSeat.getSeat().getSeatNumber())
                .toList());
        printTicketRequest.setTrainName(ticket.getSchedule().getTrain().getTrainName());
        printTicketRequest.setDepartureStation(ticket.getDepartureStation().getName());
        printTicketRequest.setArrivalStation(ticket.getArrivalStation().getName());
        return printTicketRequest;
    }
}
