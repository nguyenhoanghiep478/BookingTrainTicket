package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public interface ITicketService {
    TicketDTO bookingTicket(CreateTicketRequest request) throws IOException, WriterException;

    List<TicketDTO> getAll();

    boolean updateStatusAfterScanQR(String text);

    byte[] generateQRCode(String s) throws IOException, WriterException;

    String readQRCode(byte[] qrBytes) throws IOException;

    List<TicketDTO> getByDepartureNameArrivalNameAndDepartureTime(String departureStationName, String arrivalStationName, Timestamp departureTime);

    TicketDTO toDTO(Ticket ticket);
}
