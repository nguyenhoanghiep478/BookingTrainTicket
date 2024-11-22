package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.request.CreateRailcarRequest;
import com.backend.store.interfacelayer.dto.response.CreateRailcarResponse;
import com.backend.store.interfacelayer.dto.response.RailcarDTO;
import com.backend.store.interfacelayer.service.railcar.IRailcarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        List<RailcarDTO> response = railcarService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get all railcar successful"))
                .result(response)
                .build());
    }
}
