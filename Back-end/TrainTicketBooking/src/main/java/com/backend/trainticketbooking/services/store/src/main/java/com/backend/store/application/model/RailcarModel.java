package com.backend.store.application.model;

import com.backend.store.core.domain.state.RailcarType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RailcarModel {
    private Integer id;
    private String name;
    private RailcarType railcarType;
    private Integer capacity;
    private Integer seatPerRow;
    private Boolean isHaveFloor;
    private Integer trainId;
}
