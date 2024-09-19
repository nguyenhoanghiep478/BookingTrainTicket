package com.backend.store.core.domain.entity.schedule;

import com.backend.store.core.domain.entity.AbstractEntity;
import com.backend.store.core.domain.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Station extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",unique = true)
    private Address address;
    private String name;

    @OneToMany(mappedBy = "station",cascade = CascadeType.ALL)
    private List<ScheduleStation> schedules;

    @OneToMany(mappedBy = "station")
    private List<RouteStation> routeStations;
}
