package com.backend.store.interfacelayer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateScheduleRequest {
    private Integer trainId;
    private Integer routeId;
    private String startTime;
}
