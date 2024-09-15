package com.booksms.authentication.application.usecase;


import com.booksms.authentication.application.BaseUsecase;
import com.booksms.authentication.application.model.PermissionModel;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.core.entity.Permission;
import com.booksms.authentication.core.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetPermissionUseCase implements BaseUsecase<UserModel,Set<PermissionModel>> {
    private final IUserRepository userRepository;

    @Override
    public Set<PermissionModel> execute(UserModel user) {
        var existedUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", user.getEmail()))
        );

        return userRepository.findPermissionById(existedUser.getId())
                .stream()
                .map(permission -> {
                    PermissionModel permissionModel = new PermissionModel();
                    permissionModel.setCode(permission.getCode());
                    permissionModel.setGroupCode(permission.getGroupCode());
                    return permissionModel;
                }).collect(Collectors.toSet());

    }
}
