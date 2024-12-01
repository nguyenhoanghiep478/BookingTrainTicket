package com.booksms.authentication.interfaceLayer.service.impl;

import com.booksms.authentication.core.constant.STATIC_VAR;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.interfaceLayer.service.IJwtBlackListService;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.booksms.authentication.core.constant.STATIC_VAR.AUTHOR_STRING_TOKEN;

@Service
@RequiredArgsConstructor
public class jwtService implements IJwtService {
    private final IJwtBlackListService jwtBlackListService;
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;
    @Override
    public String generateToken(UserCredential userCredential,String[] permissions) {
        return generateToken(new HashMap<>(), userCredential,permissions);
    }

    @Override
    public String isValidToken(String token) {
        return !isExpiredToken(token)? extractUsername(token) : null;
    }
    @Override
    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<String> permissions = claims.get(AUTHOR_STRING_TOKEN,List.class);
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    @Override
    public Boolean isBlacklisted(String token) {
        return jwtBlackListService.isBlackList(token);
    }

    @Override
    public void addToBlacklist(String token) {
        jwtBlackListService.addToBlackList(token);
    }

    @Override
    public String generateRefreshToken(UserCredential userCredential, String[] permissionsByUserCredential) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userCredential.getEmail())
                .claim(AUTHOR_STRING_TOKEN,permissionsByUserCredential)
                .claim("id",userCredential.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Integer extractId(String token) {
        return Integer.parseInt(extractClaims(token,Claims::getId));
    }

    @Override
    public Boolean isExpiredToken(String token) {
        return extractExpirationToken(token).before(new Date());
    }

    private Date extractExpirationToken(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
           return Jwts
                   .parser()
                   .setSigningKey(getSignKey())
                   .build()
                   .parseClaimsJws(token)
                   .getPayload();


    }

    private String generateToken(Map<String,Object> extraClaims, UserCredential user,String[] permission) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .claim(AUTHOR_STRING_TOKEN,permission)
                .claim("id",user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
