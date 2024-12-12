package com.booksms.authentication.application.EventListener;

import com.booksms.authentication.application.event.UserConnectEvent;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserInformationDTO;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserConnectListener {
    private final RedisService redisService;

    @EventListener
    public void UserConnectEvent(UserConnectEvent event) {
        UserInformationDTO userInformationDTO = redisService.getUserSession(event.getUserName());
        if (userInformationDTO == null) return;

        userInformationDTO.addSessions(event.getSessionIndex());
        userInformationDTO.addIpAddress(event.getClientIp());

        redisService.addUserSession(event.getUserName(), userInformationDTO);
    }

}
