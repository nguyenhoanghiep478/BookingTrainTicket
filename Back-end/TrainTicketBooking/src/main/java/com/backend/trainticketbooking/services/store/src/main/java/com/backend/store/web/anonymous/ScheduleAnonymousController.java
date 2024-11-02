package com.backend.store.web.anonymous;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule/anonymous")
@RequiredArgsConstructor
public class ScheduleAnonymousController {
    private final IScheduleService scheduleService;

    @GetMapping("/get-schedule/{id}")
    public ResponseEntity<?> getSchedule(@PathVariable("id") Integer id) {
        List<ScheduleDTO> response = scheduleService.findScheduleById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get schedule successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-round-trip")
    public ResponseEntity<?> getScheduleRoundTrip(
            @RequestParam("departureStationId") Integer departureStationId,
            @RequestParam("arrivalStationId") Integer arrivalStationId,
            @RequestParam("scheduleId") Integer scheduleId
            ) {
        List<ScheduleDTO> response = scheduleService.findRoundTrip(departureStationId,arrivalStationId,scheduleId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get round trip successful"))
                .result(response)
                .build());
    }
}
