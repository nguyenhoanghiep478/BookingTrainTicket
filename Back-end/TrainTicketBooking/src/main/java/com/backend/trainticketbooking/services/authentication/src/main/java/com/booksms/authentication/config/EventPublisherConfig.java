package com.booksms.authentication.config;

import com.booksms.authentication.application.eventPublisher.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventPublisherConfig {

    public EventPublisherConfig(ApplicationEventPublisher applicationEventPublisher) {

        EventPublisher.getInstance().initialize(applicationEventPublisher);
    }
}
