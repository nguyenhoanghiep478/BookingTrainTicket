package com.booksms.authentication.config;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OAuth2AuthorizationRequestUtils {
    public static Map<String, Object> convertToMap(OAuth2AuthorizationRequest authorizationRequest) {
        return Map.of(
                "authorizationUri", authorizationRequest.getAuthorizationUri(),
                "responseType", authorizationRequest.getResponseType().getValue(),
                "clientId", authorizationRequest.getClientId(),
                "redirectUri", authorizationRequest.getRedirectUri(),
                "scopes", authorizationRequest.getScopes(),
                "state", authorizationRequest.getState(),
                "additionalParameters", authorizationRequest.getAdditionalParameters(),
                "attributes", authorizationRequest.getAttributes(),
                "grantType", authorizationRequest.getGrantType().getValue()
        );
    }

    public static OAuth2AuthorizationRequest convertFromMap(Map<String, Object> map) {
        return OAuth2AuthorizationRequest.authorizationCode()
                .authorizationUri((String) map.get("authorizationUri"))
                .clientId((String) map.get("clientId"))
                .redirectUri((String) map.get("redirectUri"))
                .scopes(new HashSet<>((List<String>) map.get("scopes")))
                .state((String) map.get("state"))
                .additionalParameters((Map<String, Object>) map.get("additionalParameters"))
                .attributes((Map<String, Object>) map.get("attributes"))
                .build();
    }
}
