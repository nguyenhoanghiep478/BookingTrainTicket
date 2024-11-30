package com.backend.store.interfacelayer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifiedRailcarsToTrainRequest {
    @NotNull(message = "train id is required")
    Integer trainId;
    @NotNull(message = "railcars id are required")
    Set<Integer> railcarIds;

}
