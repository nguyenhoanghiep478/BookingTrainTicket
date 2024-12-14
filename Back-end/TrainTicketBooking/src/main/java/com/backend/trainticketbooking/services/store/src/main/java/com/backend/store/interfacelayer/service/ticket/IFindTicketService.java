package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface IFindTicketService {
    List<Ticket> getAll();

    List<NotificationRequest> findTicketBetween(LocalDateTime now, LocalDateTime oneHourFromNow);

    List<Ticket> findTicketByDepartureIdAndArrivalIdAndDepartureTime(Integer id, Integer id1, Timestamp departureTime);

    List<Ticket> findTicketByCustomerName(String customerName);

    Ticket getById(int id);

    List<Ticket> getByOrderNumber(Long orderNumber);

    List<Ticket> getByEmail(String email);
}
