package com.booksms.authentication.config;

import com.booksms.authentication.interfaceLayer.service.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

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
        try {
            String token = header.replace("Bearer ", "");
            if (jwtService.isValidToken(token) == null) {
                throw new RuntimeException("Invalid token");
            }
            List<SimpleGrantedAuthority> permissions = jwtService.extractAuthorities(token);

            String username = jwtService.extractUsername(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, permissions);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error(e.getMessage());
            SecurityContextHolder.clearContext();

        }

        filterChain.doFilter(request, response);

    }
}
