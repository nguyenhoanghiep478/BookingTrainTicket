package com.backend.store.interfacelayer.dto.objectDTO;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDetailDTO {
    private int id;
    private String name;
    private List<StationDTO> stations;
}
