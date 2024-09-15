package com.booksms.authentication.config;

import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.core.entity.UserCredential;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public Class<Role> roleClass() {
        return Role.class;
    }

    @Bean
    public Class<UserCredential> userCredentialClass() {
        return UserCredential.class;
    }

}
