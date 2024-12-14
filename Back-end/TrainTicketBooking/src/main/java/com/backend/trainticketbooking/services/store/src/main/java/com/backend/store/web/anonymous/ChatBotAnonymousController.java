package com.backend.store.web.anonymous;

import com.backend.store.infrastructure.servicegateway.IAssistantService;
import com.backend.store.infrastructure.servicegateway.IChatBotService;
import com.backend.store.infrastructure.servicegateway.impl.AssistantService;
import com.backend.store.interfacelayer.dto.objectDTO.ChatBotRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/store/anonymous")
@RequiredArgsConstructor
public class ChatBotAnonymousController {
    private final AssistantService assistantService;

    @PostMapping("/chat")
    public String analyzeMessage(HttpServletRequest clientRequest, @RequestBody ChatBotRequest request) throws JsonProcessingException {

        if(request.getId() == null){
            String clientIp = getClientIp(clientRequest);
            request.setId(clientIp);
        }
        return assistantService.getResponse(request.getId(),request.getMessage());
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Trong trường hợp `X-Forwarded-For` có nhiều địa chỉ, lấy địa chỉ đầu tiên
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
