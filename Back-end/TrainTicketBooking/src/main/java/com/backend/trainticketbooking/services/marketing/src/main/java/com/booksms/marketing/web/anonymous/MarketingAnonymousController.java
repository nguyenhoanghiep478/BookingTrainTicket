package com.booksms.marketing.web.anonymous;

import com.booksms.marketing.interfaceLayer.dto.VerifyUserDTO;
import com.booksms.marketing.interfaceLayer.dto.response.ResponseDTO;
import com.booksms.marketing.interfaceLayer.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marketing/anonymous")
@RequiredArgsConstructor
public class MarketingAnonymousController {
    private final IEmailService emailService;

    @PostMapping("/verify-token")
    public ResponseEntity<?> validateToken(@RequestBody VerifyUserDTO verifyUserDTO){
        String message=  emailService.verifyToken(verifyUserDTO);
        return ResponseEntity.ok(ResponseDTO.builder()
                        .status(200)
                        .message(List.of(message))
                        .result(message)
                .build());
    }
}
