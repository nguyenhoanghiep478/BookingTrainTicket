package com.backend.store.core.domain.entity.Booking;

import com.backend.store.core.domain.entity.AbstractEntity;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.schedule.TicketSeat;
import com.backend.store.core.domain.entity.train.Seat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer customerId;
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "departure_station_id")
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "arrival_station_id")
    private Station arrivalStation;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
    private List<TicketSeat> ticketSeats;

    public List<TicketSeat> getTicketSeats() {
        if(ticketSeats == null){
            ticketSeats = new LinkedList<>();
        }
        return this.ticketSeats;
    }
}