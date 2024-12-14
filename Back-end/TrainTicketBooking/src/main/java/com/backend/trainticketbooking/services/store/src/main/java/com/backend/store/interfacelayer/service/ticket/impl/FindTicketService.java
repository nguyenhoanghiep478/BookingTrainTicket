package com.backend.store.interfacelayer.service.ticket.impl;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.usecase.Ticket.FindTicketUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.infrastructure.servicegateway.IOrderService;
import com.backend.store.interfacelayer.dto.objectDTO.OrderDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import com.backend.store.interfacelayer.service.ticket.IFindTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindTicketService implements IFindTicketService {
    private final FindTicketUseCase findTicketUseCase;

    private final IOrderService orderService;
    @Override
    public List<Ticket> getAll() {
        return findTicketUseCase.execute(null);
    }

    @Override
    public List<NotificationRequest> findTicketBetween(LocalDateTime now, LocalDateTime oneHourFromNow) {
        return findTicketUseCase.findTicketBetween(now,oneHourFromNow);
    }

    @Override
    public List<Ticket> findTicketByDepartureIdAndArrivalIdAndDepartureTime(Integer id, Integer id1, Timestamp departureTime) {
        return findTicketUseCase.findTicketByDepartureIdAndArrivalIdAndDepartureTime(id,id1,departureTime);
    }

    @Override
    public List<Ticket> findTicketByCustomerName(String customerName) {
        List<Ticket> tickets = findTicketUseCase.execute(null);
        return tickets.stream().filter(ticket -> ticket.getCustomerName().equals(customerName)).toList();
    }

    @Override
    public Ticket getById(int id) {
        return findTicketUseCase.findById(id);
    }

    @Override
    public List<Ticket> getByOrderNumber(Long orderNumber) {
        List<OrderDTO> orderDTO = orderService.findByOrderNumber(orderNumber);
        if(orderDTO == null){
            return null;
        }
        List<Ticket> tickets = new ArrayList<>();
        for(OrderDTO item : orderDTO){
            Ticket ticket =  findTicketUseCase.findById(item.getTicketId());
            tickets.add(ticket);
        }
        return tickets;
    }

    @Override
    public List<Ticket> getByEmail(String email) {
        Criteria criteria = Criteria.builder()
                .key("email")
                .operation(":")
                .value(email)
                .build();

        return findTicketUseCase.execute(List.of(criteria));
    }


}
