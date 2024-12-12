package com.booksms.authentication.application.EventListener;

import com.booksms.authentication.application.event.UserLoginMultipleEvent;
import com.booksms.authentication.core.constant.STATIC_VAR;
import com.booksms.authentication.interfaceLayer.DTO.Request.MultipleLoginInfoDTO;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserInformationDTO;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import com.booksms.authentication.interfaceLayer.websocket.CustomHandleWebSocket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Random;

import static com.booksms.authentication.core.constant.STATIC_VAR.*;

@Component
@Slf4j
public class UserLoginMultipleListener {
    @Autowired
    private  RedisService redisService;
    @Autowired
    private  CustomHandleWebSocket customHandleWebSocket;
    @EventListener
    public void handleUserLoginMultipleEvent(UserLoginMultipleEvent userLoginMultipleEvent) throws Exception {
        UserInformationDTO userInformationDTO = redisService.getUserSession(userLoginMultipleEvent.getUserName());
        WebSocketSession session = customHandleWebSocket.getSessionByIndex(userInformationDTO.getSessions().stream().findFirst().get());
        Integer requestId = userLoginMultipleEvent.getRequestId();
        customHandleWebSocket.handleTextMessage(session,new TextMessage(URL_ACCEPT_MULTIPLE_LOG_IN+requestId));
        MultipleLoginInfoDTO infoDTO = new MultipleLoginInfoDTO();
        infoDTO.setAccessToken(userLoginMultipleEvent.getAccessToken());
        infoDTO.setRefreshToken(userLoginMultipleEvent.getRefreshToken());
        infoDTO.setUserName(userLoginMultipleEvent.getUserName());
        redisService.addMultipleLoginToRedis(requestId,infoDTO);
    }
}
