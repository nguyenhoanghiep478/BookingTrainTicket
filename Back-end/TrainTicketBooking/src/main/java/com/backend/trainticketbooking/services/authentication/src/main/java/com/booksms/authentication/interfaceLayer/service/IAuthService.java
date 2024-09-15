package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.interfaceLayer.DTO.Request.*;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.UserResponseDTO;

import javax.security.sasl.AuthenticationException;
import java.util.List;

public interface IAuthService {
    UserDTO register(RegisterRequest request);
    Boolean validateToken(String token);
    AuthResponse login(AuthRequest request);

    Integer getTotalUser();

    UserDTO findById(Integer id);

    AuthResponse refershToken(String token, String refreshToken) throws AuthenticationException;

    void createResetPasswordRequest(CreateResetPasswordRequest request);

    void updatePassword(ResetPasswordRequest request);

    List<UserResponseDTO> getAll();

    UserResponseDTO getById(int id);
}
