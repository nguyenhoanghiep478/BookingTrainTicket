package com.backend.store.application.model;

import com.backend.store.core.domain.state.TrainType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainModel {
    private Integer id;
    private String trainNumber;
    private String trainName;
    private TrainType trainType;
    private Integer capacity;
    private Integer totalRailCars;
    private Integer currentStationId;
    private Set<Integer> railcarIds;
}
