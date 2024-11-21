package com.booksms.authentication.web.anonymous;

import com.booksms.authentication.core.exception.InvalidSessionLoginException;
import com.booksms.authentication.interfaceLayer.DTO.Request.*;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.UserResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) throws InvalidSessionLoginException {
        AuthResponse response = authService.login(request);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", response.getRefreshToken().getValue())
                .httpOnly(true) // Đặt cookie HTTP-only
                .secure(true) // Nếu sử dụng HTTPS
                .path("/") // Cookie áp dụng cho toàn bộ ứng dụng
                .maxAge(7 * 24 * 60 * 60) // Thời gian sống của cookie (7 ngày)
                .sameSite("Strict") // Ngăn chặn gửi cookie đến bên thứ ba
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(ResponseDTO.builder()
                        .status(200)
                        .message(Collections.singletonList("login successful"))
                        .result(response)
                        .build())
                ;
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh-token",defaultValue = "") String refreshToken) throws AuthenticationException {
        AuthResponse response = authService.refreshToken(null,refreshToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", response.getRefreshToken().getValue())
                .httpOnly(true) // Đặt cookie HTTP-only
                .secure(true) // Nếu sử dụng HTTPS
                .path("/") // Cookie áp dụng cho toàn bộ ứng dụng
                .maxAge(7 * 24 * 60 * 60) // Thời gian sống của cookie (7 ngày)
                .sameSite("Strict") // Ngăn chặn gửi cookie đến bên thứ ba
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(ResponseDTO.builder()
                        .status(200)
                        .message(Collections.singletonList("login successful"))
                        .result(response)
                        .build())
                ;
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser() {
        List<UserResponseDTO> response = authService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("get all user successful"))
                .result(response)
                .build());
    }

}
