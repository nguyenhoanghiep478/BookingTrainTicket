package com.booksms.authentication.interfaceLayer.DTO.Response;

import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private ResponseCookie refreshToken;
    private UserDTO profile;
}
