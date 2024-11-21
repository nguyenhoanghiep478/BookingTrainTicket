package com.booksms.authentication.web.controller;

import com.booksms.authentication.interfaceLayer.DTO.Request.DeleteUserRequest;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.UserResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final IAuthService authService;


    @GetMapping("/total-user")
    public ResponseEntity<?> totalUser() {
        Integer total = authService.getTotalUser();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("login successful"))
                .result(total)
                .build());
    }

    @GetMapping("/log-out")
    public ResponseEntity<?> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token
            ,@CookieValue(value = "refresh-token",required = false) String refreshToken) {
        authService.logOut(token,refreshToken);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("log out successful"))
                .build());
    }

    @PostMapping("/hard-delete")
    public ResponseEntity<?> hardDeleteUser(@RequestBody DeleteUserRequest request){
        authService.hardDeleteById(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("delete user successful"))
                .build());
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

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        UserResponseDTO response = authService.getById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("get user by id successful"))
                .result(response)
                .build());
    }


    @GetMapping("/validate-token")
    public Boolean validateToken(@RequestHeader("Authorization") String token) {
        return authService.validateToken(token);
    }


}
