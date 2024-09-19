package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;
import com.backend.store.interfacelayer.dto.response.CreateRailcarResponse;
import com.backend.store.interfacelayer.service.railcar.IRailcarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/railcar")
@RequiredArgsConstructor
public class RailcarController {
    private final IRailcarService railcarService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CreateRailcarRequest request) {
        CreateRailcarResponse response = railcarService.create(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                        .status(200)
                        .message(List.of("create railcar successful"))
                        .result(response)
                .build());

    }
}
