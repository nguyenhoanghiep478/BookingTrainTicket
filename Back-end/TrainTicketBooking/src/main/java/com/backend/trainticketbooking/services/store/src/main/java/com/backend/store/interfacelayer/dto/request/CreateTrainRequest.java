package com.backend.store.interfacelayer.dto.request;

import com.backend.store.core.domain.state.TrainType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTrainRequest {
    @NotNull(message = "train number is required")
    @Pattern(regexp = "\\d+", message = "Train number must be a numeric value")
    private String trainNumber;
    @NotNull(message = "train name is required")
    private String trainName;
    @NotNull(message = "train type is required")
    private TrainType trainType;
    @NotNull(message = "current Station id is required")
    Integer currentStationId;
}
