package com.booksms.authentication.application.eventPublisher;

import com.booksms.authentication.application.event.UserLogoutEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishEventLogoutUserId(Integer userId) {
        log.info("Publishing UserLogoutEvent for user: {}", userId);
        UserLogoutEvent userLogoutEvent = new UserLogoutEvent(this,userId);
        eventPublisher.publishEvent(userLogoutEvent);
    }

}
