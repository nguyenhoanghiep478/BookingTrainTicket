package com.backend.store.web.anonymous;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.response.RailcarDTO;
import com.backend.store.interfacelayer.service.railcar.IRailcarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/railcar/anonymous")
@RequiredArgsConstructor
public class RailcarAnonymousController {
    private final IRailcarService railcarService;

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
