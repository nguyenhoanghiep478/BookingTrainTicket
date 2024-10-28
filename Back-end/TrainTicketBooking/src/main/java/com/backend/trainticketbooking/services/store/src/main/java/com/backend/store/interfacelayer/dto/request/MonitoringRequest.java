package com.backend.store.interfacelayer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringRequest {
    private Integer trainId;
    private Integer scheduleId;
    private Integer currentStationId;
}
