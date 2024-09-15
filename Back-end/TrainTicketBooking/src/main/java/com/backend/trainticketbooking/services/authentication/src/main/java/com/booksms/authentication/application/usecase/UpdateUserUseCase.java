package com.booksms.authentication.application.usecase;

import com.booksms.authentication.application.BaseUsecase;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.exception.UpdateFailureException;
import com.booksms.authentication.core.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase implements BaseUsecase<UserModel, UserModel> {
    private final IUserRepository repository;

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
        if(userModel.getAddress() != null){
            credential.setAddress(userModel.getAddress());
        }
        if(userModel.getIsVerified() != null){
            credential.setVerified(userModel.getIsVerified());
        }
        if(userModel.getPassword() != null){
            credential.setPassword(userModel.getPassword());
        }
        if(userModel.getIsFirstVisit() != null){
            credential.setFirstVisit(userModel.getIsFirstVisit());
        }
        return credential;
    }
}
