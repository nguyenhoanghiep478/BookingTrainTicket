package com.booksms.authentication.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class UserLogoutEvent extends ApplicationEvent {
    private final Integer userId;

    public UserLogoutEvent(Object source,Integer userId) {
        super(source);
        this.userId = userId;
    }


}
