package com.backend.store.interfacelayer.dto.response;

import com.backend.store.core.domain.state.TrainType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTrainResponse {
    private String trainNumber;
    private String trainName;
    private TrainType trainType;
    private Integer capacity;
    private Integer totalRailCars;
}
