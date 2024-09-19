package com.backend.store.interfacelayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRouteResponse {
    private Integer id;
    private String name;
    private List<String> stationName;
}
