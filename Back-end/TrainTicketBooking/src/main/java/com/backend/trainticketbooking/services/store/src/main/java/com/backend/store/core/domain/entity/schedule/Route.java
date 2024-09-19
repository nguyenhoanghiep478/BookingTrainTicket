package com.backend.store.core.domain.entity.schedule;

import com.backend.store.core.domain.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
public class Route extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    @OneToMany(mappedBy = "route",cascade = CascadeType.ALL)
    private List<RouteStation> routeStations;

    @OneToMany(mappedBy = "route",cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    public List<RouteStation> getRouteStations() {
        if(routeStations == null)return new LinkedList<RouteStation>();
        return routeStations;
    }

}
