package com.booksms.authentication.config;

import com.booksms.authentication.core.constant.STATIC_VAR;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.booksms.authentication.core.constant.STATIC_VAR.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
       config.enableSimpleBroker(WEBSOCKET_TOPIC_LOGOUT_ENDPOINT);
       config.setApplicationDestinationPrefixes(WEBSOCKET_PREFIX_ENDPOINT);
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WEBSOCKET_CONNECT_ENDPOINT)
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }
}
