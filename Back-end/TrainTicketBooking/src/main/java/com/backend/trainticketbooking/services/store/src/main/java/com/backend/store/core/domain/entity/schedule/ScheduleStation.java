package com.backend.store.core.domain.entity.schedule;

import com.backend.store.core.domain.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "schedule_station")
public class ScheduleStation extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(nullable = false)
    private Timestamp departureTime;
    @Column(nullable = false)
    private Timestamp arrivalTime;
    @Column(nullable = false)
    private Integer stopSequence;

    @Transient
    private Long travelTime;

    @Column
    private Timestamp actualDepartureTime;

    @Column
    private Timestamp actualArrivalTime;

    @Column
    private Long actualTravelTime;
}
