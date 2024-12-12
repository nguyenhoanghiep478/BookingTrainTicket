package com.booksms.authentication.interfaceLayer.websocket;

import com.booksms.authentication.application.eventPublisher.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class CustomHandleWebSocket extends TextWebSocketHandler {
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userName = session.getAttributes().get("userName").toString();
        String clientIp = session.getAttributes().get("clientIp").toString();

        EventPublisher.getInstance().publishEventUserConnect(userName,clientIp,sessions.size());

        sessions.add(session);
        log.info("Session added by thread: {}, Total sessions: {}", Thread.currentThread().getName(), sessions.size());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Khi một client ngắt kết nối
        System.out.println("WebSocket connection closed: " + session.getId());
        sessions.remove(session);
        log.info("Session removed: {}, Total sessions: {}", session.getId(), sessions.size());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Xử lý tin nhắn từ client
        System.out.println("Message received: " + message.getPayload());

        // Phản hồi tin nhắn lại client
        session.sendMessage(new TextMessage(message.getPayload()));
    }
    public WebSocketSession getSessionByIndex(int index){
        log.info("Fetching session by thread: {}, Total sessions: {}", Thread.currentThread().getName(), sessions.size());
        return this.sessions.get(index);
    }
    // Phương thức gửi tin nhắn đến tất cả các client
    public void sendMessageToAllClients(String message) throws Exception {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}
