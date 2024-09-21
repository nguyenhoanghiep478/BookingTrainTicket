package com.booksms.authentication.web.anonymous;

import com.booksms.authentication.interfaceLayer.DTO.Request.*;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth/anonymous")
@RequiredArgsConstructor
@Slf4j
public class AuthAnonymousController {
    private final IAuthService authService;

    @PostMapping("/pre-reset-password")
    public ResponseEntity<?> preResetPassword(@RequestBody @Valid CreateResetPasswordRequest request) {
        authService.createResetPasswordRequest(request);

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("send reset password successful"))
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
         authService.updatePassword(request);

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("reset password successful"))
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        var user = authService.register(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("register user successful"))
                .result(user)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,response.getRefreshToken().getValue())
                .body(ResponseDTO.builder()
                        .status(200)
                        .message(Collections.singletonList("login successful"))
                        .result(response)
                        .build())
                ;
    }



}
