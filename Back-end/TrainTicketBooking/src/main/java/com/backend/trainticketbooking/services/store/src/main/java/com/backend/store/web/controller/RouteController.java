package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.RouteDTO;
import com.backend.store.interfacelayer.dto.objectDTO.RouteDetailDTO;
import com.backend.store.interfacelayer.dto.request.CreateRouteRequest;
import com.backend.store.interfacelayer.dto.response.CreateRouteResponse;
import com.backend.store.interfacelayer.service.route.IRouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRoutes(){
        List<RouteDTO> response = routeService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get all route successful"))
                .result(response)
                .build());
    }
    @GetMapping("/get-all-detail")
    public ResponseEntity<?> getAllDetailRoutes(){
        List<RouteDetailDTO> response = routeService.getAllDetail();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get all route successful"))
                .result(response)
                .build());
    }
}
