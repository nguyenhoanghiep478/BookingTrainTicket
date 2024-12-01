package com.booksms.authentication.application.usecase;

import com.booksms.authentication.application.BaseUsecase;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.exception.PasswordTheSameWithOldPassWordException;
import com.booksms.authentication.core.exception.UpdateFailureException;
import com.booksms.authentication.core.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase implements BaseUsecase<UserModel, UserModel> {
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel execute(UserModel model) {
        UserCredential userCredential = repository.findByEmail(model.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        userCredential = merge(userCredential, model);
        repository.save(userCredential).orElseThrow(
                 ()  -> new UpdateFailureException("update user failed,please try again")
         );

         return model;
    }

    public UserCredential merge(UserCredential credential,UserModel userModel) {
        if(userModel == null) {
            return credential;
        }
        if(userModel.getPhone() != null){
            credential.setPhone(userModel.getPhone());
        }
        if(userModel.getFirstName() != null) {
            credential.setFirstName(userModel.getFirstName());
        }

        if(userModel.getLastName() != null) {
            credential.setLastName(userModel.getLastName());
        }

        if(userModel.getAddress() != null){
            credential.setAddress(userModel.getAddress());
        }
        if(userModel.getIsVerified() != null){
            credential.setIsVerified(userModel.getIsVerified());
        }
        if(userModel.getPassword() != null){
            validNotTheSameWithOldPassword(credential,userModel.getPassword());
            if(userModel.getOldPassword() != null){
                validUpdatePassword(credential, userModel);
                credential.setPassword(passwordEncoder.encode(userModel.getPassword()));
            }else{
                credential.setPassword(passwordEncoder.encode(userModel.getPassword()));
            }


        }
        if(userModel.getIsFirstVisit() != null){
            credential.setIsFirstVisit(userModel.getIsFirstVisit());
        }
        return credential;
    }

    private void validNotTheSameWithOldPassword(UserCredential credential, String password) {
        if(passwordEncoder.matches(password, credential.getPassword())){
            throw new PasswordTheSameWithOldPassWordException("your old password the same with new password");
        }
    }

    private void validUpdatePassword(UserCredential credential, UserModel userModel) {
        if(passwordEncoder.matches(userModel.getOldPassword(), credential.getPassword() )) {
            return;
        }
        throw new UpdateFailureException("wrong password ,please try again");
    }
}
