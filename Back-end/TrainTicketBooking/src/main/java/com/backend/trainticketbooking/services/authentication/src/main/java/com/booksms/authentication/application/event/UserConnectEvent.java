package com.booksms.authentication.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserConnectEvent extends ApplicationEvent {
    private final String userName;
    private final Integer sessionIndex;
    private final String clientIp;


    public UserConnectEvent(Object source, String userName, Integer session, String clientIp) {
        super(source);
        this.userName = userName;
        this.sessionIndex = session;
        this.clientIp = clientIp;
    }
}
