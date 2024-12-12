package com.backend.store.web.anonymous;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/get-by-departure-and-arrival")
    public ResponseEntity<?> getScheduleByDepartAndArrival(
            @RequestParam("departureStationId") Integer departureStationId,
            @RequestParam("arrivalStationId") Integer arrivalStationId,
            @RequestParam(value = "departureTime",required = false) String departureTime
    ) {
        Timestamp sqlTimestamp = null;
        if(departureTime!=null){
            try {
                departureTime = departureTime.replace(" ", "+");


                OffsetDateTime odt = OffsetDateTime.parse(departureTime);

                // Chuyển đổi sang Timestamp
                sqlTimestamp = Timestamp.valueOf(odt.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Invalid timestamp format: " + e.getMessage());
            }
        }

        List<ScheduleDTO> response = scheduleService.findScheduleByDepartAndArrivalAndDepartureTime(departureStationId,arrivalStationId,sqlTimestamp);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get schedule by departure and arrival successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-by-departure-and-arrival-name")
    public ResponseEntity<?> getScheduleByDepartAndArrivalName(
            @RequestParam(value = "departureStation",required = false) String departureStationId,
            @RequestParam(value = "arrivalStation",required = false) String arrivalStationId,
            @RequestParam(value = "departureTime",required = false) String departureTime,
            @RequestParam(value = "arrivalTime" ,required = false) String arrivalTime
    ) {
        Timestamp departureTimestamp = null;
        Timestamp arrivalTimestamp = null;
        if(departureTime!=null){
            departureTimestamp = scheduleService.toTimeStamp(departureTime);
        }

        if(arrivalTime != null){
            arrivalTimestamp = scheduleService.toTimeStamp(arrivalTime);
        }

        List<ScheduleDTO> response = scheduleService.findScheduleByDepartAndArrivalNameAndDepartureTime(departureStationId,arrivalStationId,departureTimestamp);

            if(arrivalTimestamp !=null){
                List<ScheduleDTO> responseRoundTrip = null;
                if(response != null){
                     responseRoundTrip = scheduleService.findRoundTripWithName(departureStationId,arrivalStationId,response.get(0).getId(),arrivalTimestamp);
                }
                List<List<ScheduleDTO>> roundTripResponse = new ArrayList<>();
                roundTripResponse.add(response);
                roundTripResponse.add(responseRoundTrip);
                return ResponseEntity.ok(ResponseDTO.builder()
                        .status(200)
                        .message(List.of("get schedule by departure and arrival successful"))
                        .result(roundTripResponse)
                        .build());
            }


        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get schedule by departure and arrival successful"))
                .result(response)
                .build());
    }
    @GetMapping("/get-schedules-available")
    public ResponseEntity<?> getSchedulesAvailable() {
        Map<Integer,List<ScheduleDTO>> response = scheduleService.findAllAvailableSchedules();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get all available schedules successful"))
                .result(response)
                .build());
    }
}
