package com.booksms.authentication.config;

import com.booksms.authentication.core.exception.AccessDeniedHandle;
import com.booksms.authentication.core.exception.SpringSecurityException.CustomEntryPoint;
import com.booksms.authentication.interfaceLayer.service.impl.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOauth2UserService customOauth2UserService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final AccessDeniedHandle accessDeniedHandle;
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomEntryPoint();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/websocket/auth",
                                "/api/v1/auth/anonymous/*",
                                "/api/v1/auth/anonymous/oauth2/login",
                                "/api/v1/marketing/anonymous/*",
                                "/api/v1/payment/anonymous/*",
                                "/api/v1/order/anonymous/*",
                                "/api/v1/distributor/anonymous/*",
                                "/api/v1/auth/anonymous/v3/api-docs/**",
                                "/api/v1/auth/anonymous/v3/api-docs/public/**",
                                "/api/v1/auth/anonymous/swagger-ui/**",
                                "/api/v1/auth/anonymous/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2->oauth2
                        .userInfoEndpoint(userInfo->userInfo
                                .userService(customOauth2UserService)
                        )
                        .successHandler(oauth2SuccessHandler)
                        .authorizationEndpoint(ae ->
                                        ae.authorizationRequestRepository(new HttpCookieOAuth2AuthorizationRequestRepository())
                                )


                )
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandle))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DefaultOAuth2AuthorizationRequestResolver resolver(ClientRegistrationRepository clientRegistrationRepository){
        return new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/login");
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> cookieAuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
}
