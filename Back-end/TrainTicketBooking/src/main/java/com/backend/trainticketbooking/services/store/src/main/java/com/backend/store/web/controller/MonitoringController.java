package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.SeatDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.MonitoringRequest;
import com.backend.store.interfacelayer.dto.response.MonitoringResponse;
import com.backend.store.interfacelayer.service.monitoring.IMonitoringService;
import com.backend.store.interfacelayer.service.seat.IFindSeatService;
import com.backend.store.interfacelayer.service.seat.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/monitoring")
public class MonitoringController {
    private final IMonitoringService monitoringService;
    private final ISeatService seatService;
    @PutMapping("/update-current-station")
    public ResponseEntity<?> updateCurrentStation(@RequestBody MonitoringRequest request) {
        MonitoringResponse response = monitoringService.updateScheduleOfTrain(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("update trip of train successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-seat-available-in-ids")
    public ResponseEntity<?> getSeatAvailableById(@RequestParam List<Integer> ids,@RequestParam Integer scheduleId,@RequestParam Integer departureStationId) {
        List<SeatDTO> response = seatService.checkSeat(ids,scheduleId,departureStationId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("check seat successful"))
                .result(response)
                .build());
    }
}
