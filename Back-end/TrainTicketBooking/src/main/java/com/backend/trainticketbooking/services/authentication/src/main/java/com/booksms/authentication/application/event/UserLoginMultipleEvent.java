package com.booksms.authentication.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserLoginMultipleEvent extends ApplicationEvent {
    private final String userName;
    private final String accessToken;
    private final String refreshToken;
    private final Integer requestId;
    public UserLoginMultipleEvent(Object source, String userName, String accessToken, String refreshToken,Integer requestId) {
        super(source);
        this.userName = userName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.requestId = requestId;
    }
}
