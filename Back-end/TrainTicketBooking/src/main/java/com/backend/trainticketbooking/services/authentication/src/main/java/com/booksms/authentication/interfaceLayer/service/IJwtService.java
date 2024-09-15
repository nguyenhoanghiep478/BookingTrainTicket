package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.core.entity.UserCredential;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface IJwtService {
    String generateToken(UserCredential userCredential,String[] permission);
    String isValidToken(String token);
    Boolean isExpiredToken(String token);
    List<SimpleGrantedAuthority> extractAuthorities(String token);
    String extractUsername(String token);

    String generateRefreshToken(UserCredential userCredential, String[] permissionsByUserCredential);
}
