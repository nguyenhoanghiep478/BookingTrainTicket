package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<?> bookingTicket(@RequestBody @Valid CreateTicketRequest request) {
        TicketDTO response = ticketService.bookingTicket(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("booking ticket successful"))
                .result(response)
                .build());
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTickets() {
        List<TicketDTO> response = ticketService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get ticket successful"))
                .result(response)
                .build());
    }
    
}
