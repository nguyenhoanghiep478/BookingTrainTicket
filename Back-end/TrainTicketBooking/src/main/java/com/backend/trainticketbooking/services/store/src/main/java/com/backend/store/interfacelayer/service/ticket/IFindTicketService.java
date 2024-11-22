package com.backend.store.interfacelayer.service.ticket;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface IFindTicketService {
    List<Ticket> getAll();

    List<NotificationRequest> findTicketBetween(LocalDateTime now, LocalDateTime oneHourFromNow);
}
