package com.booksms.gateway.filter;

import com.booksms.gateway.ServiceGateway.AuthService;
import com.booksms.gateway.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private AuthService authService;

    AuthenticationFilter() {
       super(Config.class);
   }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange,chain)->{
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new UnauthorizedException("missing token");
                }
                String jwt = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                try{
                    authService.validateToken(jwt);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
