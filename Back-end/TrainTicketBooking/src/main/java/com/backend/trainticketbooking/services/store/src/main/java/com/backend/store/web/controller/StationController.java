package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.request.CreateStationRequest;
import com.backend.store.interfacelayer.dto.response.CreateStationResponse;
import com.backend.store.interfacelayer.service.station.IStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/station")
@RequiredArgsConstructor
public class StationController {
    private final IStationService stationService;

    @PostMapping("/create")
    public ResponseEntity<?> createStation(@RequestBody CreateStationRequest station) {
        CreateStationResponse response = stationService.create(station);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("create station successful"))
                .result(response)
                .build());
    }
}
