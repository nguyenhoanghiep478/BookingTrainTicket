package com.booksms.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoint = List.of(
            "/api/v1/auth/anonymous/",
            "/api/v1/marketing/anonymous/",
            "/api/v1/payment/anonymous/",
            "/api/v1/order/anonymous/",
            "/api/v1/train/anonymous/",
            "api/v1/schedule/anonymous",
            "api/v1/ticket/anonymous/",
            "api/v1/station/anonymous/",
            "api/v1/railcar/anonymous/",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoint
                .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
