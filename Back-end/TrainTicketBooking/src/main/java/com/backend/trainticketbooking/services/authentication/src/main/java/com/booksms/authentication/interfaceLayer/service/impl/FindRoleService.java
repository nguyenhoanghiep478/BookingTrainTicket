package com.booksms.authentication.interfaceLayer.service.impl;

import com.booksms.authentication.application.usecase.FindRoleUseCase;
import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.interfaceLayer.service.IFindRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindRoleService implements IFindRoleService {
    private final FindRoleUseCase findRoleUseCase;

    @Override
    public List<Role> findAll() {
        return findRoleUseCase.execute(null);
    }
}
