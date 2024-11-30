package com.booksms.authentication.interfaceLayer.service.impl;

import com.booksms.authentication.application.model.PermissionModel;
import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.application.usecase.*;
import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.exception.*;
import com.booksms.authentication.interfaceLayer.DTO.Request.*;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.UpdateUserResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.UserResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseCookie;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.booksms.authentication.core.constant.STATIC_VAR.MAX_FAILED_ATTEMPTS;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService  implements IAuthService {
    private final RegisterUseCase registerUseCase;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final GetPermissionUseCase getPermissionUseCase;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FindUserUseCase findUserUseCase;
    private final KafkaTemplate<String, NewUserRegister> kafkaTemplate;
    private final KafkaTemplate<String, UserDTO> kafkaResetPasswordTemplate;
    private final UpdateUserUseCase updateUserUseCase;
    private final RedisService redisService;
    private final HandleFailedAttemptLoginUseCase handleFailedAttemptLoginUseCase;
    private final HardDeleteUserUseCase hardDeleteUserUseCase;


    @Override
    public UserDTO register(RegisterRequest request) {
           request.setPassword(passwordEncoder.encode(request.getPassword()));
           var user = registerUseCase.execute(modelMapper.map(request, UserModel.class));
           kafkaTemplate.send("UserRegister", NewUserRegister.builder()
                           .firstName(user.getFirstName())
                           .lastName(user.getLastName())
                           .isVerified(false)
                           .isFirstVisit(true)
                           .recipient(user.getEmail())
                   .build());
           request.setId(user.getId());
           return modelMapper.map(user, UserDTO.class);
    }

    public String generateToken(String username) {
        UserCredential userCredential = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                        .key("email")
                        .operation(":")
                        .value(username)
                .build())
        ).get(0);
        return jwtService.generateToken(userCredential,getPermissionsByUserCredential(userCredential));
    }

    @Override
    public Boolean validateToken(String jwt) {
        String token = jwt.substring(7);
        log.info(token);
        String id = jwtService.isValidToken(token);
        if(id == null){
            return false;
        }
        List<UserCredential> userCredential = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                .key("id")
                .operation(":")
                .value(id)
                .build())
        );
        return !userCredential.isEmpty();
    }

    @Override
    public AuthResponse login(AuthRequest request) throws InvalidSessionLoginException {
        UserCredential userCredential = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                        .key("email")
                        .operation(":")
                        .value(request.getEmail())
                        .build()))
                .get(0);
        if(redisService.isUserWorking(userCredential.getEmail())){
            throw new InvalidSessionLoginException("User has login in another place");
        }

        try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));


                handleFailedAttemptLoginUseCase.resetFailedAttempts(userCredential);
                if(userCredential.getIsBlocked()){
                    throw new UserBlockedException(String.format("User %s is blocked", userCredential.getEmail()));
                }
                if(!userCredential.getIsVerified()){
                    kafkaTemplate.send("UserRegister", NewUserRegister.builder()
                            .firstName(userCredential.getFirstName())
                            .lastName(userCredential.getLastName())
                            .isVerified(false)
                            .isFirstVisit(true)
                            .isBlocked(false)
                            .recipient(userCredential.getEmail())
                            .build());
                    throw new UserNotVerifiedException(String.format("User %s is not verified", userCredential.getEmail()));
                }

                String accessToken = generateToken(request.getEmail());
                redisService.addUserSession(userCredential.getEmail(),accessToken);
                return AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(ResponseCookie.from("refresh-token",generateRefreshToken(request.getEmail()))
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .build())
                        .profile(findById(userCredential.getId()))
                        .build();
        }catch (BadCredentialsException e){
            handleFailedAttemptLoginUseCase.increaseFailedAttempts(userCredential);
            if (userCredential.getFailAttempt() >= MAX_FAILED_ATTEMPTS) {
                handleFailedAttemptLoginUseCase.lockUser(userCredential);
                throw new UserBlockedException("you has been blocked because login failed too much time");
            }
        }

        throw new BadCredentialsException("please check your email/password");
    }

    private String generateRefreshToken(String email) {
        UserCredential userCredential = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                .key("email")
                .operation(":")
                .value(email)
                .build())
        ).get(0);
        return jwtService.generateRefreshToken(userCredential,getPermissionsByUserCredential(userCredential));
    }

    @Override
    public Integer getTotalUser() {
        return findUserUseCase.execute(null).size();
    }

    @Override
    public UserDTO findById(Integer id) {
        SearchUserCriteria fieldId=  SearchUserCriteria.builder()
                .key("id")
                .operation(":")
                .value(String.valueOf(id))
                .build();

        return modelMapper.map(findUserUseCase.execute(List.of(fieldId)).get(0), UserDTO.class);
    }

    public UserDTO findByEmail(String email) {
        SearchUserCriteria fieldId=  SearchUserCriteria.builder()
                .key("email")
                .operation(":")
                .value(String.valueOf(email))
                .build();

        return modelMapper.map(findUserUseCase.execute(List.of(fieldId)).get(0), UserDTO.class);
    }

    @Override
    public AuthResponse refreshToken(String jwt, String refreshToken) throws AuthenticationException {
//        String token = jwt.substring(7);
//        log.info(token);
//        String id = jwtService.isValidToken(token);
//        if(id == null){
//           throw new AuthenticationException("invalid access token");
//        }

        if(jwtService.isExpiredToken(refreshToken)){
            throw new AuthenticationException("invalid refresh token");
        }

        String email = jwtService.extractUsername(refreshToken);
        return AuthResponse.builder()
                .accessToken(generateToken(email))
                .refreshToken(ResponseCookie.from("refresh-token",generateRefreshToken(email))
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .build())
                .build();
    }

    @Override
    public void addUserSession(Integer userId) {

    }

    @Override
    public void createResetPasswordRequest(CreateResetPasswordRequest request) {
        UserDTO userDTO = findByEmail(request.getEmail());
        if(userDTO == null){
            throw new UserNotFoundException("email not found");
        }
        Random random = new Random();
        int randomInt = Math.abs(random.nextInt());

        redisService.setValue(randomInt,userDTO);
        userDTO.setId(randomInt);
        kafkaResetPasswordTemplate.send("ResetPassword",userDTO);
    }

    @Override
    public void updatePassword(ResetPasswordRequest request) {
        UserDTO userDTO = redisService.getValue(request.getId());
        if(userDTO == null){
            throw new UserNotFoundException("email not found");
        }
        UserModel userModel = modelMapper.map(userDTO, UserModel.class);
        userModel.setPassword(request.getPassword());
        updateUserUseCase.execute(userModel);
    }

    @Override
    public List<UserResponseDTO> getAll() {
        List<UserCredential> userCredentials = findUserUseCase.execute(null);

        return map(userCredentials);
    }

    @Override
    public UserResponseDTO getById(int id) {
        SearchUserCriteria fieldId=  SearchUserCriteria.builder()
                .key("id")
                .operation(":")
                .value(String.valueOf(id))
                .build();
        return map(findUserUseCase.execute(List.of(fieldId))).get(0);
    }

    @Override
    public void logOut(String token, String refreshToken) {
        token = token.replace("Bearer ","");
        jwtService.addToBlacklist(token);
        if(refreshToken != null){
            jwtService.addToBlacklist(refreshToken);
        }
        String userName =  jwtService.extractUsername(token);
        redisService.removeUserSession(userName);

    }

    @Override
    public void hardDeleteById(DeleteUserRequest request) {
        hardDeleteUserUseCase.execute(request);
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
       UserModel model =  updateUserUseCase.execute(modelMapper.map(request,UserModel.class));

       return modelMapper.map(model, UpdateUserResponse.class);
    }

    private List<UserResponseDTO> map(List<UserCredential> userCredentials) {
        return userCredentials.stream()
                .map(user -> {
                    UserResponseDTO response =  modelMapper.map(user,UserResponseDTO.class);
                    if(user.getRoles() != null || !user.getRoles().isEmpty()){
                        response.setRoleName(user.getRoles().stream().map(Role::getName).toList());
                    }
                    return response;
                })
                .toList();
    }


    private String[] getPermissionsByUserCredential(UserCredential userCredential) {
        Set<PermissionModel> permissionModels = getPermissionUseCase.execute(modelMapper.map(userCredential, UserModel.class));
        return permissionModels.stream().map(PermissionModel::getCode).toArray(String[]::new);
    }

    @KafkaListener(id="consumer-user-register-response",topics = "UserRegisterResponse")
    private void verifyUserCredential(NewUserRegister userRegister) {
        UserModel user = modelMapper.map(userRegister, UserModel.class);
        user.setEmail(userRegister.getRecipient());
        updateUserUseCase.execute(user);
    }


    @Scheduled(fixedRate = 60000) // Chạy mỗi 1 phút
    public void unlockAccounts() {
        List<UserCredential> userCredentials = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                .key("isBlocked")
                .operation(":")
                .value(true)
                .build()));
        List<UserCredential> blockedByFailedAttemptLogin = userCredentials.stream().filter(userCredential -> userCredential.getLockTime() != null).toList();
        blockedByFailedAttemptLogin.forEach(handleFailedAttemptLoginUseCase::unlockUser);
    }

}
