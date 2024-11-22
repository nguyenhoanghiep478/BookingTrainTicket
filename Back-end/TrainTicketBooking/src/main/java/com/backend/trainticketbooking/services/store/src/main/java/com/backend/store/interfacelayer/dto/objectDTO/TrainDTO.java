package com.backend.store.interfacelayer.dto.objectDTO;

import com.backend.store.core.domain.state.TrainStatus;
import com.backend.store.core.domain.state.TrainType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainDTO {
    private Integer id;
    private String trainNumber;
    private String trainName;
    private List<ScheduleDTO> schedules;
    private TrainType trainType;
    private Integer capacity;
    private Integer totalRailCars;
    private TrainStatus trainStatus;
    private String currentStation;
}
