package com.backend.store.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableSeatNumberForSchedule {
    private Integer scheduleId;
    private String railcarName;
    private List<String> seatNumbers;
    private List<String> seatIds;
}
