package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.StatisticRevenueInDateDTO;
import com.backend.store.interfacelayer.dto.objectDTO.StatisticRevenueInMonthDTO;
import com.backend.store.interfacelayer.dto.objectDTO.StatisticRevenueInYearDTO;
import com.backend.store.interfacelayer.dto.objectDTO.StatisticTicketByCustomerDTO;
import com.backend.store.interfacelayer.service.statistic.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final IStatisticService statisticService;

    @GetMapping("/get-revenue-in-date")
    public ResponseEntity<?> getRevenueInDate(@RequestParam int year,@RequestParam int month, @RequestParam int day){
        List<StatisticRevenueInDateDTO> response = statisticService.getStatisticRevenueInDate(year,month,day);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get statistic successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-revenue-in-month")
    public ResponseEntity<?> getRevenueInMonth(@RequestParam int year,@RequestParam int month){
        List<StatisticRevenueInMonthDTO> response = statisticService.getStatisticRevenueInMonth(year,month);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get statistic successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-revenue-in-year")
    public ResponseEntity<?> getRevenueInYear(@RequestParam int year){
        List<StatisticRevenueInYearDTO> response = statisticService.getStatisticRevenueInYear(year);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get statistic successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-ticket-by-customer")
    public ResponseEntity<?> getTicketByCustomer(@RequestParam String customerName){
        StatisticTicketByCustomerDTO response = statisticService.getTicketByCustomerName(customerName);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get statistic successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-ticket-by-customer-and-departure")
    public ResponseEntity<?> getTicketByCustomerAndDeparture(@RequestParam String customerName,@RequestParam String departureStationName) {
        StatisticTicketByCustomerDTO response = statisticService.getTicketByCustomerNameAndDeparture(customerName, departureStationName);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get statistic successful"))
                .result(response)
                .build());
    }


}
