package com.backend.store.core.domain.entity.train;

import com.backend.store.core.domain.entity.AbstractEntity;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.state.TrainStatus;
import com.backend.store.core.domain.state.TrainType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "train")
public class Train extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String trainNumber;
    @Column(nullable = false)
    private String trainName;
    @Enumerated(EnumType.STRING)
    private TrainType trainType;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private Integer totalRailCars;
    @OneToMany(mappedBy = "train")
    private Set<Railcar> railcars ;
    @OneToMany(mappedBy = "train")
    private List<Schedule> schedules;
    @Enumerated(EnumType.STRING)
    private TrainStatus trainStatus;
    @OneToOne
    @JoinColumn(nullable = false,unique = true,name = "current_station_id")
    private Station currentStation;

    @PrePersist
    protected void onCreate() {
        if(this.capacity != null|| this.totalRailCars != null || railcars == null)return;
        for(Railcar railcar : railcars) {
            this.capacity += railcar.getCapacity();
        }
        this.totalRailCars = railcars.size();
    }

    public Set<Railcar> getRailcars() {
        if(railcars == null)return new LinkedHashSet<>();
        return railcars;
    }
}
