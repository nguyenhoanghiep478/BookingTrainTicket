package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.request.MonitoringRequest;
import com.backend.store.interfacelayer.dto.response.MonitoringResponse;
import com.backend.store.interfacelayer.service.monitoring.IMonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/monitoring")
public class MonitoringController {
    private final IMonitoringService monitoringService;
    @PutMapping("/update-current-station")
    public ResponseEntity<?> updateCurrentStation(@RequestBody MonitoringRequest request) {
        MonitoringResponse response = monitoringService.updateScheduleOfTrain(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("update trip of train successful"))
                .result(response)
                .build());
    }
}
