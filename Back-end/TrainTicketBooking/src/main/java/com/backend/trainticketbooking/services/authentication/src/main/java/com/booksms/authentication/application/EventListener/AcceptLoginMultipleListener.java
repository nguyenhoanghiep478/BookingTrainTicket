package com.booksms.authentication.application.EventListener;

import com.booksms.authentication.application.event.UserLoginEvent;
import com.booksms.authentication.interfaceLayer.DTO.Request.MultipleLoginInfoDTO;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserInformationDTO;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcceptLoginMultipleListener {
    private final RedisService redisService;
    @EventListener
    public void acceptLoginMultiple(UserLoginEvent userLoginEvent) {
        String accessToken = null;
        String refreshToken = null;
        String userName = null;
        UserInformationDTO userInformationDTO = null;
        if(userLoginEvent.getIdMultiple() != null){
            MultipleLoginInfoDTO loginInfoDTO = redisService.getMultipleLogin(userLoginEvent.getIdMultiple());
            accessToken = loginInfoDTO.getAccessToken();
            refreshToken = loginInfoDTO.getRefreshToken();
            userName = loginInfoDTO.getUserName();
            userInformationDTO = redisService.getUserSession(userName);

            userInformationDTO.addAccessToken(accessToken);
            userInformationDTO.addRefreshToken(refreshToken);

            redisService.removeMultipleLoginFromRedis(userLoginEvent.getIdMultiple());
        }else{
            accessToken = userLoginEvent.getAccessToken();
            refreshToken = userLoginEvent.getRefreshToken();
            userName = userLoginEvent.getUserName();
            userInformationDTO = new UserInformationDTO();
            userInformationDTO.addAccessToken(accessToken);
            userInformationDTO.addRefreshToken(refreshToken);
            userInformationDTO.setUserId(userLoginEvent.getUserId());

        }

        redisService.addUserSession(userName, userInformationDTO);
    }
}
