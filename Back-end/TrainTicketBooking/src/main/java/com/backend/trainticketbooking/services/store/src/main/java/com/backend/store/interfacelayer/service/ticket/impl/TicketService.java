package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.state.TicketStatus;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.backend.store.interfacelayer.service.QRCode.IQRCodeService;
import com.backend.store.interfacelayer.service.station.IFindStationService;
import com.backend.store.interfacelayer.service.ticket.ICreateTicketService;
import com.backend.store.interfacelayer.service.ticket.IFindTicketService;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import com.backend.store.interfacelayer.service.ticket.IUpdateTicketService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ICreateTicketService createTicketService;
    private final IQRCodeService qrCodeService;
    private final IFindTicketService findTicketService;
    private final IUpdateTicketService updateTicketService;
    private final IFindStationService findStationService;

    @Override
    public TicketDTO bookingTicket(CreateTicketRequest request) throws IOException, WriterException {
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

    @Override
    public byte[] generateQRCode(String ticketId) throws IOException, WriterException {
        return qrCodeService.generateQRCode(ticketId);
    }

    @Override
    public String readQRCode(byte[] qrBytes) throws IOException {
        String ticketId= qrCodeService.readQRCode(qrBytes);
        return updateStatusAfterScanQR(ticketId) ? ticketId : null;
    }

    @Override
    public List<TicketDTO> getByDepartureNameArrivalNameAndDepartureTime(String departureStationName, String arrivalStationName, Timestamp departureTime) {
        Station departureStation = null;
        Station arrivalStation = null;
        List<Ticket> tickets;

        if(departureStationName != null){
            departureStation = findStationService.findByName(departureStationName);
        }
        if(arrivalStationName != null) {
            arrivalStation = findStationService.findByName(arrivalStationName);
        }
        Integer departureStationId = departureStation != null ? departureStation.getId() : null;
        Integer arrivalStationId = arrivalStation != null ? arrivalStation.getId() : null;

        if(departureStationId != null && arrivalStationId != null && departureTime != null) {
            tickets =  findTicketService.findTicketByDepartureIdAndArrivalIdAndDepartureTime(departureStationId,arrivalStationId,departureTime);
        }else{
            tickets = findTicketService.getAll();
            if(departureStationId != null){
               tickets= tickets.stream().filter(ticket -> ticket.getDepartureStation().getId().equals(departureStationId)).toList();
            }

            if(arrivalStationId != null){
                tickets = tickets.stream().filter(ticket -> ticket.getArrivalStation().getId().equals(arrivalStationId)).toList();
            }

            if(departureTime != null){
                tickets = tickets.stream().filter(ticket -> ticket.getDepartureStation().getSchedules().stream().filter(scheduleStaion -> scheduleStaion.getStopSequence().equals(1)).findFirst().get().getDepartureTime().after(departureTime)).toList();
            }
        }

        return tickets.stream().map(this::toDTO).toList();
    }


    public TicketDTO toDTO(Ticket ticket) {
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
                .build();
    }
}
