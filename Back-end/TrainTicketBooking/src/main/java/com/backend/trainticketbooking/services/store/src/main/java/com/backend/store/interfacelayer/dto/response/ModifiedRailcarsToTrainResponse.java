package com.backend.store.interfacelayer.dto.response;

import com.backend.store.core.domain.state.TrainType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifiedRailcarsToTrainResponse {
    private String trainNumber;
    private String trainName;
    private TrainType trainType;
    private Integer capacity;
    private Integer totalRailCars;
}
