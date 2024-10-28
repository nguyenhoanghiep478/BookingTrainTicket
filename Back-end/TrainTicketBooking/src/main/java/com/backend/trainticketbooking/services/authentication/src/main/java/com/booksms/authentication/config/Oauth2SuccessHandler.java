package com.booksms.authentication.config;

import com.booksms.authentication.application.model.PermissionModel;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.application.usecase.GetPermissionUseCase;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {
    private final IJwtService jwtService;
    private final ModelMapper modelMapper;
    private final GetPermissionUseCase getPermissionUseCase;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(
                modelMapper.map(oAuth2User.getAttributes(), UserCredential.class),
                getPermissionsByUserCredential(modelMapper.map(oAuth2User.getAttributes(), UserCredential.class))
        );

        String refreshToken = jwtService.generateRefreshToken(
                modelMapper.map(oAuth2User.getAttributes(), UserCredential.class),
                getPermissionsByUserCredential(modelMapper.map(oAuth2User.getAttributes(), UserCredential.class))
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                String.format("{\"accessToken\": \"%s\", \"refreshToken\": \"%s\"}", accessToken, refreshToken)
        );

        // Thêm refresh-token vào cookie
        Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);

//        response.sendRedirect("/home");
    }

    private String[] getPermissionsByUserCredential(UserCredential userCredential) {
        Set<PermissionModel> permissionModels = getPermissionUseCase.execute(modelMapper.map(userCredential, UserModel.class));
        return permissionModels.stream().map(PermissionModel::getGroupCode).toArray(String[]::new);
    }
}
