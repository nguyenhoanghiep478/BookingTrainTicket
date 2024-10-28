package com.bookms.order.infrastructure.FeignClient;

import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "store-service",url = "http://localhost:5555/api/v1/monitoring")
public interface StoreClient {
    @GetMapping("/get-seat-available-in-ids")
    ResponseEntity<ResponseDTO> getSeatAvailableById(@RequestParam List<Integer> ids, @RequestParam Integer scheduleId, @RequestParam Integer departureStationId);
}
