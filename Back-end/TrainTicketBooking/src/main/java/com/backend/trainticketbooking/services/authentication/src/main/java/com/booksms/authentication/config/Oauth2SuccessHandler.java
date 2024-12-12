package com.booksms.authentication.config;

import com.booksms.authentication.application.eventPublisher.EventPublisher;
import com.booksms.authentication.application.model.PermissionModel;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.application.usecase.GetPermissionUseCase;
import com.booksms.authentication.core.constant.STATIC_VAR;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.interfaceLayer.DTO.Request.MultipleLoginInfoDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

import static com.booksms.authentication.core.constant.STATIC_VAR.EXPIRATION_REFRESH_JWT_IN_MILLISECONDS;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {
    private final IJwtService jwtService;
    private final ModelMapper modelMapper;
    private final GetPermissionUseCase getPermissionUseCase;
    private final RedisService redisService;
    @Value("${HOME_REDIRECT_URL}")
    private String homeURl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info(oAuth2User.getAttributes().toString());
        UserCredential userCredential = modelMapper.map(oAuth2User.getAttributes(), UserCredential.class);

        String accessToken = jwtService.generateToken(
                userCredential,
                getPermissionsByUserCredential(modelMapper.map(oAuth2User.getAttributes(), UserCredential.class))
        );
        boolean isAccept = false;
        String refreshToken = jwtService.generateRefreshToken(
                userCredential,
                getPermissionsByUserCredential(modelMapper.map(oAuth2User.getAttributes(), UserCredential.class))
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                String.format("{\"accessToken\": \"%s\", \"refreshToken\": \"%s\"}", accessToken, refreshToken)
        );
        Random random = new Random();
        // Thêm refresh-token vào cookie
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token",refreshToken)
                .httpOnly(true) // Đặt cookie HTTP-only
                .secure(true) // Nếu sử dụng HTTPS
                .path("/") // Cookie áp dụng cho toàn bộ ứng dụng
                .maxAge(EXPIRATION_REFRESH_JWT_IN_MILLISECONDS) // Thời gian sống của cookie (7 ngày)
                .sameSite("Strict") // Ngăn chặn gửi cookie đến bên thứ ba
                .build();

        if (!redisService.isUserWorking(userCredential.getEmail())) {
            isAccept = true;
            EventPublisher.getInstance().publishEventUserLogin(userCredential.getEmail(),accessToken,refreshToken, userCredential.getId(), null);
        }else{

            Integer requestId = Math.abs(random.nextInt());
            EventPublisher.getInstance().publishEventMultipleLogin(userCredential.getEmail(), accessToken, refreshToken, requestId);

            // Sử dụng ExecutorService để xử lý thay vì Thread
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Boolean> future = executor.submit(() -> {
                try {
                    long start = System.currentTimeMillis();
                    while (redisService.isWaitingAccept(requestId)) {
                        if (System.currentTimeMillis() - start > 60_000) {
                            // Ngừng khi vượt quá 1 phút
                            return false;
                        }
                        Thread.sleep(100); // Giảm tải CPU
                    }
                    return !redisService.isWaitingAccept(requestId);
                } finally {
                    redisService.removeMultipleLoginFromRedis(requestId);
                }
            });

            try {
                // Chờ kết quả từ Future với timeout
                isAccept = future.get(60, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future.cancel(true); // Hủy nhiệm vụ nếu quá thời gian
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            } finally {
                executor.shutdown();
            }
        }

           


        if (isAccept) {
            int randomNumber = Math.abs(random.nextInt());
            redisService.addMultipleLoginToRedis(randomNumber, MultipleLoginInfoDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .userName(userCredential.getEmail())
                    .build());
            response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
            response.sendRedirect(homeURl + "?token=" + randomNumber);
        }else{
            response.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
            response.sendRedirect(homeURl + "/auth");
        }


    }

    private String[] getPermissionsByUserCredential(UserCredential userCredential) {
        Set<PermissionModel> permissionModels = getPermissionUseCase.execute(modelMapper.map(userCredential, UserModel.class));
        return permissionModels.stream().map(PermissionModel::getGroupCode).toArray(String[]::new);
    }
}
