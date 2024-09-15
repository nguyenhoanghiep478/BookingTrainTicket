package com.booksms.gateway.ServiceGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    @Autowired
    private AuthClient authClient;

    public Mono<Void> validateToken(String token) {
        return Mono.fromCallable(() -> authClient.validateToken(token))
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new RuntimeException("Invalid token"));
                    }
                    return Mono.empty();
                });
    }
}
