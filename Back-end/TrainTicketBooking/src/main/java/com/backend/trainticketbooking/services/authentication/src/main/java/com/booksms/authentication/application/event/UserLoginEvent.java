package com.booksms.authentication.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserLoginEvent extends ApplicationEvent {
    private final Integer userId;
    private final String userName;
    private final String accessToken;
    private final String refreshToken;
    private final Integer idMultiple;

    public UserLoginEvent(Object source,Integer userId,String userName,String accessToken,String refreshToken,Integer idMultiple) {
        super(source);
        this.userName = userName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.idMultiple = idMultiple;
        this.userId = userId;
    }
}
