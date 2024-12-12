package com.booksms.authentication.application.eventPublisher;

import com.booksms.authentication.application.event.UserConnectEvent;
import com.booksms.authentication.application.event.UserLoginEvent;
import com.booksms.authentication.application.event.UserLoginMultipleEvent;
import com.booksms.authentication.application.event.UserLogoutEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventPublisher {
    private  ApplicationEventPublisher eventPublisher;
    private static EventPublisher instance;

    public static EventPublisher getInstance() {
        if (instance == null) {
            synchronized (EventPublisher.class) { // Đồng bộ hóa để đảm bảo thread-safe
                if (instance == null) { // Kiểm tra lại trong block synchronized
                    instance = new EventPublisher();
                }
            }
        }
        return instance;
    }
    public void initialize(ApplicationEventPublisher eventPublisher) {
        if (this.eventPublisher == null) { // Đảm bảo chỉ khởi tạo 1 lần
            this.eventPublisher = eventPublisher;
        } else {
            throw new IllegalStateException("EventPublisher has already been initialized");
        }
    }
    public void publishEventLogoutUser(String accessToken,Boolean isBLocked) {
        log.info("Publishing UserLogoutEvent for token: {}", accessToken);
        UserLogoutEvent userLogoutEvent = new UserLogoutEvent(this,accessToken,isBLocked);
        eventPublisher.publishEvent(userLogoutEvent);
    }

    public void publishEventUserConnect(String userName, String clientIp, Integer sessionIndex){
        UserConnectEvent userLoginEvent = new UserConnectEvent(this,userName,sessionIndex,clientIp);
        eventPublisher.publishEvent(userLoginEvent);
    };

    public void publishEventMultipleLogin(String userName,String accessToken,String refreshToken,Integer requestId){
        UserLoginMultipleEvent userLoginMultipleEvent = new UserLoginMultipleEvent(this,userName,accessToken,refreshToken,requestId);
        eventPublisher.publishEvent(userLoginMultipleEvent);
    }


    public void publishEventUserLogin(String userName,String accessToken,String refreshToken,Integer userId,Integer requestId){
        UserLoginEvent userLoginEvent = new UserLoginEvent(this,userId,userName,accessToken,refreshToken,requestId);
        eventPublisher.publishEvent(userLoginEvent);
    }
}
