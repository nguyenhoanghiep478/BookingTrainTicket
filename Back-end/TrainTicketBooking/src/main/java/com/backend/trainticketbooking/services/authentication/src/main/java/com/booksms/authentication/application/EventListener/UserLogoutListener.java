package com.booksms.authentication.application.EventListener;

import com.booksms.authentication.application.event.UserLogoutEvent;
import com.booksms.authentication.core.constant.STATIC_VAR;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.booksms.authentication.core.constant.STATIC_VAR.WEBSOCKET_TOPIC_LOGOUT_ENDPOINT;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserLogoutListener {
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleUserLogoutEvent(UserLogoutEvent event) {
        Integer userId = event.getUserId();

        log.info("Handling logout event for user: {}", userId);
        messagingTemplate.convertAndSend(WEBSOCKET_TOPIC_LOGOUT_ENDPOINT, "User " + userId + " has been logged out");
    }
}
