package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.state.TicketStatus;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.backend.store.interfacelayer.service.ticket.ICreateTicketService;
import com.backend.store.interfacelayer.service.ticket.IFindTicketService;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import com.backend.store.interfacelayer.service.ticket.IUpdateTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ICreateTicketService createTicketService;
    private final IFindTicketService findTicketService;
    private final IUpdateTicketService updateTicketService;

    @Override
    public TicketDTO bookingTicket(CreateTicketRequest request) {
        Ticket ticket = createTicketService.bookingTicket(request);
        return toDTO(ticket);
    }

    @Override
    public List<TicketDTO> getAll() {
        List<Ticket> tickets = findTicketService.getAll();
        return tickets.stream().map(
                this::toDTO
        ).toList();
    }

    @Override
    public boolean updateStatusAfterScanQR(String text) {
        Integer ticketId = Integer.valueOf(text);
        return updateTicketService.changeStatus(ticketId, TicketStatus.IN_USE);
    }


    private TicketDTO toDTO(Ticket ticket) {
        Train train = ticket.getTicketSeats().stream()
                .map(ticketSeat -> ticketSeat.getSeat().getRailcar().getTrain()).toList().get(0);
        return TicketDTO.builder()
                .arrivalStationName(ticket.getArrivalStation().getName())
                .departureStationName(ticket.getDepartureStation().getName())
                .seatNumber(ticket.getTicketSeats().stream().map(ticketSeat -> ticketSeat.getSeat().getSeatNumber()).toList())
                .departureTime(ticket.getSchedule().getScheduleStations()
                        .stream()
                        .filter(scheduleStation -> scheduleStation.getStation().equals(ticket.getDepartureStation()))
                        .toList()
                        .get(0).getDepartureTime()
                )
                .price(ticket.getTotalPrice())
                .trainName(train.getTrainName())
                .build();
    }
}
