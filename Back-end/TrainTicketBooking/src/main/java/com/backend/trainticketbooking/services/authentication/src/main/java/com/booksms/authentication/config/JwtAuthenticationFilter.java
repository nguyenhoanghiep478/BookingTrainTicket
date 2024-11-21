package com.booksms.authentication.config;

import com.booksms.authentication.core.constant.STATIC_VAR;
import com.booksms.authentication.core.exception.BlackListedTokenException;
import com.booksms.authentication.core.exception.TokenExpiration;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;


@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;
    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthenticationFilter(IJwtService jwtService,
                                 @Qualifier("handlerExceptionResolver")  HandlerExceptionResolver exceptionResolver) {
        this.jwtService = jwtService;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

      try{
          if (request.getHeader("X-SERVICE-REQUEST") != null) {
              Authentication authentication = new UsernamePasswordAuthenticationToken(
                      "key",
                      new User("anonymous", "", Collections.singleton(new SimpleGrantedAuthority("SYSTEM_ADMIN"))),
                      Collections.singleton(new SimpleGrantedAuthority("SYSTEM_ADMIN"))
              );

              SecurityContextHolder.getContext().setAuthentication(authentication);

              filterChain.doFilter(request, response);
              return;
          }


          String header = request.getHeader("Authorization");
          if (header == null || !header.startsWith("Bearer ")) {
              filterChain.doFilter(request, response);
              return;
          }

          String token = header.replace("Bearer ", "");
          final String username;
          try{
              username = jwtService.extractUsername(token);;
              if(jwtService.isBlacklisted(token)){
                  throw new BlackListedTokenException(STATIC_VAR.BLACKLISTED_TOKEN_MESSAGE);
              }
              if (jwtService.isValidToken(token) == null) {
                  throw new RuntimeException("Invalid token");
              }
              List<SimpleGrantedAuthority> permissions = jwtService.extractAuthorities(token);

              Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, permissions);
              SecurityContextHolder.getContext().setAuthentication(authentication);
          }catch (ExpiredJwtException e){
                log.error("Expired JWT token");
                throw new TokenExpiration(e.getMessage());
          }
          filterChain.doFilter(request, response);
      }catch (Exception e) {
          SecurityContextHolder.clearContext();
          exceptionResolver.resolveException(request, response, null, e);
      }

    }
}
