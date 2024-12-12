package com.backend.store.core.domain.entity.train;

import com.backend.store.core.domain.entity.AbstractEntity;
import com.backend.store.core.domain.repository.ISeatRepository;
import com.backend.store.core.domain.state.RailcarType;
import com.backend.store.core.domain.state.SeatClass;
import com.backend.store.infrastructure.repository.SeatRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Getter
@Setter
public class Railcar extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RailcarType railcarType;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private Integer seatPerRow;
    @Column(nullable = false)
    private Boolean isHaveFloor;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "railcar",cascade = CascadeType.ALL)
    private Set<Seat> seats;

    @PrePersist
    protected void onCreate() {
        if(seats != null){
            return;
        }
        seats = new LinkedHashSet<>();
        int capacityPerFloor = 0;
        if(isHaveFloor){
            capacityPerFloor = capacity/2;
        }else{
            capacityPerFloor = capacity;
        }

        for(int i =0 ;i< capacityPerFloor;i++){
            Seat seat = new Seat();
            seat.setSeatClass(railcarType.getSeatClass());
            seat.setIsAvailable(false);
            seat.setRailcar(this);
            seat.setSeatNumber("");
            seat.setSeatType(railcarType.getSeatType());
            seats.add(seat);
        }

        if(isHaveFloor){
            for(int i =0 ;i< capacityPerFloor;i++){
                Seat seat = new Seat();
                seat.setSeatClass(railcarType.getSeatClass());
                seat.setIsAvailable(false);
                seat.setRailcar(this);
                seat.setSeatType(railcarType.getSeatType());
                seat.setSeatNumber(String.format("%s.2%s",name,i < 10 ? "0"+i : i));
                seats.add(seat);
            }
        }
    }


}
