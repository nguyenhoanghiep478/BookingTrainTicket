package com.booksms.authentication.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketAuth {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String sendMessage(String message){
        return message;
    }
}
