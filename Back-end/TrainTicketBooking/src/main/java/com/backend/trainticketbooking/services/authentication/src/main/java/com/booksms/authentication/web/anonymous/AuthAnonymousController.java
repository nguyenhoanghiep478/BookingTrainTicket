package com.booksms.authentication.web.anonymous;

import com.booksms.authentication.application.eventPublisher.EventPublisher;
import com.booksms.authentication.config.HttpCookieOAuth2AuthorizationRequestRepository;
import com.booksms.authentication.core.exception.InvalidSessionLoginException;
import com.booksms.authentication.interfaceLayer.DTO.Request.*;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.UserResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.security.sasl.AuthenticationException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/anonymous")
@RequiredArgsConstructor
@Slf4j
public class AuthAnonymousController {
    private final IAuthService authService;
    private final DefaultOAuth2AuthorizationRequestResolver resolver;
    private final RedisService redisService;


    @PostMapping("/pre-reset-password")
    public ResponseEntity<?> preResetPassword(@RequestBody @Valid CreateResetPasswordRequest request) {
        authService.createResetPasswordRequest(request);

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("send reset password successful"))
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
         authService.updatePassword(request);

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("reset password successful"))
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        var user = authService.register(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("register user successful"))
                .result(user)
                .build());
    }
    @GetMapping("/accept-multiple-log-in")
    public void acceptMultipleLogIn(@RequestParam Integer requestId) {
         EventPublisher.getInstance().publishEventUserLogin(null,null,null,null,requestId);
    }
    @PostMapping("/login")
    public DeferredResult<ResponseEntity<?>> login(@RequestBody @Valid AuthRequest request) throws InvalidSessionLoginException {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(60_000L);
        AuthResponse response = authService.login(request);
        // Xử lý timeout
        deferredResult.onTimeout(() -> {
            redisService.removeMultipleLoginFromRedis(response.getRequestId());
            deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                    .body(ResponseDTO.builder()
                            .status(408)
                            .message(Collections.singletonList("Request timeout. Please try again."))
                            .build()));
        });

        // Gọi login logic

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", response.getRefreshToken().getValue())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("None")
                .build();

        if (response.getIsMultipleLogin()) {
            // Giữ yêu cầu chờ và chỉ trả kết quả khi có sự kiện từ bên ngoài
            new Thread(() -> {
                try {
                    // Chờ điều kiện hoàn thành hoặc hết thời gian
                    long start = System.currentTimeMillis();
                    while (redisService.isWaitingAccept(response.getRequestId())) {
                        if (System.currentTimeMillis() - start > 60_000) {
                            // Ngừng khi vượt quá 1 phút
                            break;
                        }
                        Thread.sleep(100); // Giảm tải CPU
                    }

                    if (!redisService.isWaitingAccept(response.getRequestId())) {
                        deferredResult.setResult(ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                                .body(ResponseDTO.builder()
                                        .status(200)
                                        .message(Collections.singletonList("login successful"))
                                        .result(response)
                                        .build()));
                    }
                    redisService.removeMultipleLoginFromRedis(response.getRequestId());

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(ResponseDTO.builder()
                                    .status(500)
                                    .message(Collections.singletonList("Internal server error."))
                                    .build()));
                }
            }).start();
        } else {
            // Nếu response không null, trả kết quả ngay lập tức
            deferredResult.setResult(ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(ResponseDTO.builder()
                            .status(200)
                            .message(Collections.singletonList("login successful"))
                            .result(response)
                            .build()));
        }

        return deferredResult;
    }
    @GetMapping("/oauth2/login")
    public String googleLogin(HttpServletRequest request, HttpServletResponse response) throws InvalidSessionLoginException {
        OAuth2AuthorizationRequest authorizationRequest = resolver.resolve(request, "google");

        HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository = new HttpCookieOAuth2AuthorizationRequestRepository();
        authorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
        log.info("Generate url google {}",authorizationRequest.getAuthorizationRequestUri());
        return authorizationRequest.getAuthorizationRequestUri();
    }

    @GetMapping("/get-info-after-login-google")
    public ResponseEntity<?>  getInfoAfterLoginGoogle(@RequestParam Integer token){
        MultipleLoginInfoDTO info = redisService.getMultipleLogin(token);
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", info.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("None")
                .build();

        AuthResponse response = AuthResponse.builder()
                .accessToken(info.getAccessToken())
                .refreshToken(refreshTokenCookie)
                .profile(authService.findByEmail(info.getUserName()))
                .build();
        redisService.removeMultipleLoginFromRedis(token);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(ResponseDTO.builder()
                        .status(200)
                        .message(Collections.singletonList("login successful"))
                        .result(response)
                        .build());
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh-token",defaultValue = "") String refreshToken) throws AuthenticationException {
        AuthResponse response = authService.refreshToken(null,refreshToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", response.getRefreshToken().getValue())
                .httpOnly(true) // Đặt cookie HTTP-only
                .secure(true) // Nếu sử dụng HTTPS
                .path("/") // Cookie áp dụng cho toàn bộ ứng dụng
                .maxAge(7 * 24 * 60 * 60) // Thời gian sống của cookie (7 ngày)
                .sameSite("Strict") // Ngăn chặn gửi cookie đến bên thứ ba
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(ResponseDTO.builder()
                        .status(200)
                        .message(Collections.singletonList("login successful"))
                        .result(response)
                        .build())
                ;
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser() {
        List<UserResponseDTO> response = authService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("get all user successful"))
                .result(response)
                .build());
    }

}
