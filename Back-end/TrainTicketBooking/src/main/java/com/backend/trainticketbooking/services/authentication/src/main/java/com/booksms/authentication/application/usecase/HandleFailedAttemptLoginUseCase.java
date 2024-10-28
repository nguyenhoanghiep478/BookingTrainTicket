package com.booksms.authentication.application.usecase;

import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HandleFailedAttemptLoginUseCase {
    private final IUserRepository userRepository;

    public void increaseFailedAttempts(UserCredential user) {
        user.setFailAttempt(user.getFailAttempt() + 1);
        userRepository.save(user);
    }

    public void resetFailedAttempts(UserCredential user) {
        user.setFailAttempt(0);
        userRepository.save(user);
    }

    public void lockUser(UserCredential user) {
        user.setIsBlocked(true);
        user.setLockTime(LocalDateTime.now());
        userRepository.save(user);
    }

    public void unlockUser(UserCredential user) {
        user.setIsBlocked(false);
        user.setLockTime(null);
        user.setFailAttempt(0);
        userRepository.save(user);
    }
}
