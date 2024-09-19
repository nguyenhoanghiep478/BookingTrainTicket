package com.backend.store.core.domain.entity.train;

import com.backend.store.core.domain.entity.AbstractEntity;
import com.backend.store.core.domain.entity.schedule.Ticket;
import com.backend.store.core.domain.state.SeatClass;
import com.backend.store.core.domain.state.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Seat extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Column(nullable = false)
    private Boolean isAvailable = true;
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "railcar_id", nullable = false)
    private Railcar railcar;

    @PrePersist
    protected void onCreate() {
        if(price != null)return;
        this.price = seatClass.getPrice();
    }
}
