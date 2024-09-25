package com.backend.store.interfacelayer.dto.objectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {
    private String departureStationName;
    private String arrivalStationName;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String trainName;
    private List<String> seatNumbersAvailable;
}