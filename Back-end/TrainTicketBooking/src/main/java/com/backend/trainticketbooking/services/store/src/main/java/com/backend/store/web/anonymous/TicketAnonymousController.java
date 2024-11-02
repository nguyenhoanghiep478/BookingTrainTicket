package com.backend.store.web.anonymous;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket/anonymous")
@RequiredArgsConstructor
public class TicketAnonymousController {
    private final ITicketService ticketService;

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
