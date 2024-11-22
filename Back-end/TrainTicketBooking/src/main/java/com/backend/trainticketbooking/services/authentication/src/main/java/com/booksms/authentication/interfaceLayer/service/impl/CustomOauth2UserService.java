package com.booksms.authentication.interfaceLayer.service.impl;

import com.booksms.authentication.application.model.PermissionModel;
import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.application.usecase.FindUserUseCase;
import com.booksms.authentication.application.usecase.GetPermissionUseCase;
import com.booksms.authentication.application.usecase.RegisterUseCase;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final RegisterUseCase registerUseCase;
    private final FindUserUseCase findUserUseCase;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        List<UserCredential> existingUser = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                .key("email")
                .operation(":")
                .value(email)
                .build()));

        UserModel user;
        if (existingUser.isEmpty()) {
            user = new UserModel();
            user.setFirstName(oAuth2User.getAttribute("given_name"));
            user.setLastName(oAuth2User.getAttribute("family_name"));
            user.setEmail(email);
            String randomPassword = generateRandomPassword(10);
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            String encodedPassword = bCrypt.encode(randomPassword);
            user.setPassword(encodedPassword);
            user.setPhone(oAuth2User.getAttribute("phone_number"));
            user = registerUseCase.execute(user);
        } else {
            user = modelMapper.map(existingUser.get(0), UserModel.class);
        }

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                "email"
        );
    }

    private static String generateRandomPassword(int length) {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*";

        String combinedChars = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        for (int i = 4; i < length; i++) {
            password.append(combinedChars.charAt(random.nextInt(combinedChars.length())));
        }

        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars);
        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) {
            finalPassword.append(c);
        }
        return finalPassword.toString();
    }
}
