package com.backend.store.interfacelayer.dto.response;

import com.backend.store.core.domain.state.RailcarType;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRailcarResponse {
    private String name;
    private RailcarType railcarType;
    private Integer capacity;
    private Integer seatPerRow;
    private Boolean isHaveFloor;
    private String trainName;
}
