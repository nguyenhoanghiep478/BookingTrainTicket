package com.booksms.authentication.application.usecase;

import com.booksms.authentication.application.BaseUsecase;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.exception.EmailExistedException;
import com.booksms.authentication.core.exception.RegisterFailException;
import com.booksms.authentication.core.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegisterUseCase implements BaseUsecase<UserModel,UserModel> {
    private final IUserRepository userRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserModel execute(UserModel userModel) {
       var user = userRepository.findByEmail(userModel.getEmail());
       if (user.isPresent()) {
           throw new EmailExistedException(String.format("Email %s already existed", user.get().getEmail()));
       }
       UserCredential userCredential = toEntity(userModel);
       userRepository.save(userCredential).orElseThrow(
               () -> new RegisterFailException(String.format("Register for %s failed,please try again",userCredential.getEmail()))
       );

        return userModel;
    }

    private UserCredential toEntity(UserModel userModel) {
        UserCredential userCredential = new UserCredential();
        userCredential.setAddress(userModel.getAddress());
        userCredential.setFirstName(userModel.getFirstName());
        userCredential.setLastName(userModel.getLastName());
        userCredential.setPhone(userModel.getPhone());
        userCredential.setEmail(userModel.getEmail());
        userCredential.setPassword(userModel.getPassword());
        return userCredential;
    }
}
