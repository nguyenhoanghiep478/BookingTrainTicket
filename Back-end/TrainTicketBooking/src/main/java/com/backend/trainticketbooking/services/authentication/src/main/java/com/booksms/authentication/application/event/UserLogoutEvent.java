package com.booksms.authentication.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserLogoutEvent extends ApplicationEvent {
    private final String token;
    private final Boolean isBlocked;

    public UserLogoutEvent(Object source,String token,Boolean isBlocked) {
        super(source);
        this.token = token;
        this.isBlocked = isBlocked;
    }


}
