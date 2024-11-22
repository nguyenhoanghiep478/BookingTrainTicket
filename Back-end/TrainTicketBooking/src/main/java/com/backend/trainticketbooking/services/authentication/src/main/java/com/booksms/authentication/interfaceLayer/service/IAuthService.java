package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.core.exception.InvalidSessionLoginException;
import com.booksms.authentication.interfaceLayer.DTO.Request.*;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.UserResponseDTO;

import javax.security.sasl.AuthenticationException;
import java.util.List;

public interface IAuthService {
    UserDTO register(RegisterRequest request);
    Boolean validateToken(String token);
    AuthResponse login(AuthRequest request) throws InvalidSessionLoginException;

    Integer getTotalUser();

    UserDTO findById(Integer id);

    AuthResponse refreshToken(String token, String refreshToken) throws AuthenticationException;
    void addUserSession(Integer userId);
    void createResetPasswordRequest(CreateResetPasswordRequest request);

    void updatePassword(ResetPasswordRequest request);

    List<UserResponseDTO> getAll();

    UserResponseDTO getById(int id);

    void logOut(String token, String refreshToken);

    void hardDeleteById(DeleteUserRequest request);
}
