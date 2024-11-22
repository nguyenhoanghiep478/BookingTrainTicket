package com.backend.store.web.anonymous;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TrainDTO;
import com.backend.store.interfacelayer.service.train.ITrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/train/anonymous")
@RequiredArgsConstructor
public class TrainAnonymousController {
    private final ITrainService trainService;
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
