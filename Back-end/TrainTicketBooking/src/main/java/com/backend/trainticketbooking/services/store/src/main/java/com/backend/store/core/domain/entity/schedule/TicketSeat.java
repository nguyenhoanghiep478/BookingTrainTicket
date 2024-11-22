package com.backend.store.core.domain.entity.schedule;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.train.Seat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "ticket_seat")
public class TicketSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    private BigDecimal price;
}
