package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TrainDTO;
import com.backend.store.interfacelayer.dto.request.ModifiedRailcarsToTrainRequest;
import com.backend.store.interfacelayer.dto.request.CreateTrainRequest;
import com.backend.store.interfacelayer.dto.response.ModifiedRailcarsToTrainResponse;
import com.backend.store.interfacelayer.dto.response.CreateTrainResponse;
import com.backend.store.interfacelayer.service.train.ITrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/train")
@RequiredArgsConstructor
@Slf4j
public class TrainController {
    private final ITrainService trainService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CreateTrainRequest request) {
        CreateTrainResponse response = trainService.create(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("create train successful"))
                .result(response)
                .build());

    }


    @PutMapping("/add-railcars")
    public ResponseEntity<?> addRailcar(@RequestBody @Valid ModifiedRailcarsToTrainRequest request) {
        log.info(request.getTrainId().toString());
        ModifiedRailcarsToTrainResponse response = trainService.addRailcars(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("Add railcars to train successful"))
                .result(response)
                .build());
    }

    @PutMapping("/detach-railcars")
    public ResponseEntity<?> detachRailcar(@RequestBody @Valid ModifiedRailcarsToTrainRequest request){
        ModifiedRailcarsToTrainResponse response = trainService.detachRailcars(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("Detach railcars to train successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<TrainDTO> response = trainService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get all train successful"))
                .result(response)
                .build());
    }
}
