package com.booksms.authentication.application.usecase;

import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.exception.UserNotFoundException;
import com.booksms.authentication.core.repository.IUserRepository;
import com.booksms.authentication.interfaceLayer.DTO.Request.DeleteUserRequest;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class HardDeleteUserUseCase {
    private final IUserRepository userRepository;
    private final FindUserUseCase findUserUseCase;
    private final PasswordEncoder passwordEncoder;
    public void execute(DeleteUserRequest deleteUserRequest){
        UserCredential adminAccount = userRepository.findByEmail(deleteUserRequest.getUsername()).orElseThrow(
                () -> new ForbiddenException("missing permission")
        );

        if(passwordEncoder.matches(deleteUserRequest.getPassword(), adminAccount.getPassword())){
            if(adminAccount.isGlobalAdmin()){
                UserCredential userCredential = findUserUseCase.findById(deleteUserRequest.getDeleteUserId());
                userRepository.deleteByUser(userCredential);
            }
        }else{
            throw new ForbiddenException("missing permission");
        }






    }
}
