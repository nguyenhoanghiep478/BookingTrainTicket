package com.booksms.authentication.config;

import com.booksms.authentication.interfaceLayer.websocket.CustomHandleWebSocket;
import com.booksms.authentication.interfaceLayer.websocket.interceptor.WebsocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketConfigurer {
    private final CustomHandleWebSocket customHandleWebSocket;
    private final WebsocketInterceptor websocketInterceptor;
    @Value("${HOME_FRONT_END_URL}")
    private String frontEndURl;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customHandleWebSocket,"/websocket/auth")
                .addInterceptors(websocketInterceptor)
                .setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")

        ;

        ;
        ;

    }
}
