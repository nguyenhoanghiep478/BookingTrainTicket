package com.booksms.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        getCookie(request, name).ifPresent(cookie -> {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });
    }

    public static <T> String serialize(T object) {
        try {
            Map<String, Object> map = OAuth2AuthorizationRequestUtils.convertToMap((OAuth2AuthorizationRequest) object);
            return Base64.getUrlEncoder().encodeToString(objectMapper.writeValueAsString(map).getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Unable to serialize OAuth2AuthorizationRequest", e);
        }
    }

    public static <T> T deserialize(String value, Class<T> cls) {
        try {
            String json = new String(Base64.getUrlDecoder().decode(value));
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            return (T) OAuth2AuthorizationRequestUtils.convertFromMap(map);
        } catch (Exception e) {
            throw new RuntimeException("Unable to deserialize OAuth2AuthorizationRequest", e);
        }
    }
}
