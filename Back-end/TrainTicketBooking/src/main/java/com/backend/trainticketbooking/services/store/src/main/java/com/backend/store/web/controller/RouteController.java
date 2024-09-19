package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.dto.response.CreateRouteResponse;
import com.backend.store.interfacelayer.service.route.IRouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
public class RouteController {
    private final IRouteService routeService;

    @PostMapping("/create")
    public ResponseEntity<?> createRoute(@RequestBody @Valid CreateRouteRequest request){
        CreateRouteResponse response = routeService.create(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("create route successful"))
                .result(response)
                .build());
    }
}
