package com.backend.store.interfacelayer.dto.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDTO {
    private Integer id;
    private String name;
    private String departureStationName;
    private String arrivalStationName;
}
