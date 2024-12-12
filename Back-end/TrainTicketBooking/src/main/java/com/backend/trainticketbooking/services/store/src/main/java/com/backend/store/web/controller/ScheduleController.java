package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.dto.response.CreateScheduleResponse;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final IScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse response = scheduleService.create(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("create schedule successful"))
                .result(response)
                .build());
    }
    @GetMapping("/get-schedule/{id}")
    public ResponseEntity<?> getSchedule(@PathVariable("id") Integer id) {
        List<ScheduleDTO> response = scheduleService.findScheduleById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get schedule successful"))
                .result(response)
                .build());
    }


}
