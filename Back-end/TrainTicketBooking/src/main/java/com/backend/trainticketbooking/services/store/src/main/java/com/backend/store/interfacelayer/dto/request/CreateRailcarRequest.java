package com.backend.store.interfacelayer.dto.request;

import com.backend.store.core.domain.state.RailcarType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRailcarRequest {
    @NotNull(message = "railcar name is required")
    private String name;
    @NotNull(message = "railcar type is required")
    private RailcarType railcarType;
    @NotNull(message = "railcar capacity is required")
    private Integer capacity;
    @NotNull(message = "railcar seat per row is required")
    private Integer seatPerRow;
    @NotNull(message = "is have floor is required")
    private Boolean isHaveFloor;
    private Integer trainId;
}
