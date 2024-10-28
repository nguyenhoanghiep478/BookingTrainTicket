package com.backend.store.interfacelayer.dto.request;

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
public class NotificationRequest {
    private String email;
    private Timestamp departureTime;
    private String departureStation;
    private String customerName;
    private String trainName;
    private List<String> seatName;
}
