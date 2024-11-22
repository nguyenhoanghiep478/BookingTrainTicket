package com.backend.store.web.controller;

import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.dto.request.CreateTicketRequest;
import com.backend.store.interfacelayer.service.QRCode.IQRCodeService;
import com.backend.store.interfacelayer.service.ticket.ITicketService;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<?> bookingTicket(@RequestBody @Valid CreateTicketRequest request) throws IOException, WriterException {
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

    @GetMapping("/create-qr")
    public ResponseEntity<?> createQRCode(@RequestParam("ticket_id") Integer ticketId) throws IOException, WriterException {
        byte[] qrCode = ticketService.generateQRCode(String.valueOf(ticketId));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(qrCode);
    }

    @PostMapping("/read-qr")
    public ResponseEntity<String> readQRCode(@RequestParam MultipartFile qr) throws IOException {
        byte[] qrBytes = qr.getBytes();
        String result = ticketService.readQRCode(qrBytes);
        return ResponseEntity.ok(result);
    }
}
