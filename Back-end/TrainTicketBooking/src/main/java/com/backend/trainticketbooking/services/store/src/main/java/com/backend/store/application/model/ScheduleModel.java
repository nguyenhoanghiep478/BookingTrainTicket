package com.backend.store.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleModel {
    private Integer id;
    private Integer trainId;
    private Integer routeId;

}
