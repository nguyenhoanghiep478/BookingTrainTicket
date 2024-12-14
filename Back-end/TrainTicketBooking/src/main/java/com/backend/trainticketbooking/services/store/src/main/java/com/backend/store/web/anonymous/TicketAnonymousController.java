package com.backend.store.web.anonymous;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.service.ticket.IFindTicketService;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import com.backend.store.interfacelayer.service.ticket.impl.CreateTicketService;
import com.backend.store.interfacelayer.service.ticket.impl.FindTicketService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket/anonymous")
@RequiredArgsConstructor
public class TicketAnonymousController {
    private final ITicketService ticketService;
    private final IFindTicketService findTicketService;
    private final CreateTicketService createTicketService;


    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTickets() {
        List<TicketDTO> response = ticketService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get ticket successful"))
                .result(response)
                .build());
    }


    @GetMapping("/test-qr")
    public ResponseEntity<?> testQR() throws IOException, WriterException {
        Ticket ticket = findTicketService.getById(1144909301);
        createTicketService.sendQrCode(ticket);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("send ticket successful"))
                .build());
    }


    @GetMapping("/get-by-order-number")
    public ResponseEntity<?> getTicketByOrderNumber(@RequestParam("orderNumber") Long orderNumber) {
        List<TicketDTO> response = ticketService.getByOrderNumber(orderNumber);

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get ticket by order number successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<?> getTicketByEmail(@RequestParam("email") String email) {
        List<TicketDTO> response = ticketService.getByEmail(email);

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get ticket by email successful"))
                .result(response)
                .build());
    }
}
