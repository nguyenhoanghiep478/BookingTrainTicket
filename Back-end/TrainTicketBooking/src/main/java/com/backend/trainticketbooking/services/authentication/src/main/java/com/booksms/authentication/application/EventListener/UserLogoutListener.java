package com.booksms.authentication.application.EventListener;

import com.booksms.authentication.application.event.UserLogoutEvent;
import com.booksms.authentication.application.usecase.FindUserUseCase;
import com.booksms.authentication.application.usecase.HandleFailedAttemptLoginUseCase;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserInformationDTO;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import com.booksms.authentication.interfaceLayer.websocket.CustomHandleWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Set;

import static com.booksms.authentication.core.constant.STATIC_VAR.MESSAGE_LOGOUT;


@Component
@Slf4j
public  class UserLogoutListener {
    @Autowired
    private  RedisService redisService;
    @Autowired
    private  IJwtService jwtService;
    @Autowired
    private  HandleFailedAttemptLoginUseCase failedAttemptLoginUseCase;
    @Autowired
    private  FindUserUseCase findUserUseCase;
    @Autowired
    private  CustomHandleWebSocket customHandleWebSocket;

    @EventListener
    public void handleUserLogoutEvent(UserLogoutEvent event) throws Exception {
        String accessToken = event.getToken();


        accessToken = accessToken.replace("Bearer ","");
        String userName =  jwtService.extractUsername(accessToken);

        UserInformationDTO info = redisService.getUserSession(userName);

        if(event.getIsBlocked()){
            handleBLocked(userName,info);
        }else{
            handleLogOut(userName,info,accessToken);
        }

    }

    private void handleLogOut(String userName,UserInformationDTO userInformationDTO,String accessToken) throws Exception {
        List<String> tokens = userInformationDTO.getAccessToken();
        List<String> refreshTokens = userInformationDTO.getRefreshToken();
        Set<String> ipAddress = userInformationDTO.getIpAddress();
        Set<Integer> webSocketSessions = userInformationDTO.getSessions();
        boolean isNeedRemove = tokens.size() == 1;
        int index = tokens.indexOf(accessToken);
        String refreshToken = refreshTokens.get(index);

        jwtService.addToBlacklist(refreshToken);
        jwtService.addToBlacklist(accessToken);
        if(webSocketSessions.size()>=index){
            customHandleWebSocket.afterConnectionClosed(customHandleWebSocket.getSessionByIndex(index),null );
        }


        tokens.remove(index);
        refreshTokens.remove(index);
        if(webSocketSessions.size() >= index){
             webSocketSessions.remove(index);
             ipAddress.remove(index);
        }


        userInformationDTO.setAccessToken(tokens);
        userInformationDTO.setRefreshToken(refreshTokens);
        userInformationDTO.setIpAddress(ipAddress);
        userInformationDTO.setSessions(webSocketSessions);


        if(isNeedRemove){
            redisService.removeUserSession(userName);
            return;
        }
        redisService.addUserSession(userName,userInformationDTO);

    }

    private void handleBLocked(String userName,UserInformationDTO userInformationDTO) throws Exception {
        List<String> tokens = userInformationDTO.getAccessToken();
        List<String> refreshTokens = userInformationDTO.getRefreshToken();
        Set<Integer> webSocketSessions = userInformationDTO.getSessions();

        for(Integer sessionIndex : webSocketSessions){
            WebSocketSession session = customHandleWebSocket.getSessionByIndex(sessionIndex);
            customHandleWebSocket.handleTextMessage(session,new TextMessage(MESSAGE_LOGOUT));
            customHandleWebSocket.afterConnectionClosed(session,null );
        }
        for(String token : tokens){
            jwtService.addToBlacklist(token);
        }
        for(String refreshToken : refreshTokens){
            jwtService.addToBlacklist(refreshToken);
        }

        UserCredential userCredential = findUserUseCase.findByUserName(userName);
        failedAttemptLoginUseCase.lockUser(userCredential);

        redisService.removeUserSession(userName);
    }
}
