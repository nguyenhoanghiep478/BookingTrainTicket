package com.booksms.authentication.interfaceLayer.websocket.interceptor;

import com.booksms.authentication.interfaceLayer.service.IJwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
@RequiredArgsConstructor
@Slf4j
@Component
public class WebsocketInterceptor implements HandshakeInterceptor {
   private final IJwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        URI uri = request.getURI();
        String query = uri.getQuery();
        String token = null;
        if (query != null && query.contains("token=")) {
            token = query.split("token=")[1].split("&")[0]; // Lấy giá trị token từ query string
        }


        String clientIp = request.getHeaders().getFirst("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddress().getAddress().getHostAddress();
        }

        if(token == null){
            return false;
        }
        token = token.replace("Bearer ","");
        if(jwtService.isValidToken(token) == null){
            return false;
        };

        String userName = jwtService.extractUsername(token);

        attributes.put("userName", userName);
        attributes.put("clientIp", clientIp);


        log.info("User name connect {}",userName);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
