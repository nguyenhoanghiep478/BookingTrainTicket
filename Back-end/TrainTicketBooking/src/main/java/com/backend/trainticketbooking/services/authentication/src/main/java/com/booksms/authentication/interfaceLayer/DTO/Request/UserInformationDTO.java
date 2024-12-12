package com.booksms.authentication.interfaceLayer.DTO.Request;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class UserInformationDTO {
    private Integer userId;

    private Set<Integer> sessions;
    private Set<String> ipAddress;

    private List<String> accessToken;
    private List<String> refreshToken;


    public void addSessions(Integer session) {
        if(sessions == null) {
            sessions = new LinkedHashSet<>();
        };
        sessions.add(session);
    }

    public void addIpAddress(String clientIp) {
        if(ipAddress == null) {
            ipAddress = new LinkedHashSet<>();
        }
        this.ipAddress.add(clientIp);
    }

    public void addAccessToken(String token) {
        if(accessToken == null) {
            accessToken = new LinkedList<>();
        }
        this.accessToken.add(token);
    }

    public void addRefreshToken(String token) {
        if(refreshToken == null) {
            refreshToken = new LinkedList<>();
        }
        this.refreshToken.add(token);
    }
}
