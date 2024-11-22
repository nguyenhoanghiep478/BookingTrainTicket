package com.backend.store.web.anonymous;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.StationDTO;
import com.backend.store.interfacelayer.service.station.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/station/anonymous")
@RequiredArgsConstructor
public class StationAnonymousController {
    private final IStationService stationService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStations() {
        List<StationDTO> response = stationService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get schedule successful"))
                .result(response)
                .build());
    }
}
