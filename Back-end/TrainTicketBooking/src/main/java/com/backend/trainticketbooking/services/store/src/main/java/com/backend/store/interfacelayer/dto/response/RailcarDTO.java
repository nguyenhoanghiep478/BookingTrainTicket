package com.backend.store.interfacelayer.dto.response;

import com.backend.store.core.domain.state.RailcarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RailcarDTO {
    private String name;
    private RailcarType railcarType;
    private Integer capacity;
    private Integer seatPerRow;
    private Boolean isHaveFloor;
    private String trainName;
}
