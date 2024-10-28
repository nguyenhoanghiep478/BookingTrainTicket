package com.backend.store.interfacelayer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class ModifiedRailcarsToTrainRequest {
    @NotNull(message = "train id is required")
    Integer trainId;
    @NotNull(message = "railcars id are required")
    Set<Integer> railcarIds;

}
