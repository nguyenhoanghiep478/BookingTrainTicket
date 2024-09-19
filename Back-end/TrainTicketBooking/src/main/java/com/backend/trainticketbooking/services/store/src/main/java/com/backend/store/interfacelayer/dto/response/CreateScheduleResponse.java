package com.backend.store.interfacelayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateScheduleResponse {
    private String trainName;
    private String routeName;
}
