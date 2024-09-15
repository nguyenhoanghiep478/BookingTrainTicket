package com.booksms.authentication.interfaceLayer.service.impl;

import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.interfaceLayer.DTO.Response.RoleResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IFindRoleService;
import com.booksms.authentication.interfaceLayer.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final IFindRoleService findRoleService;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleResponseDTO> findAll() {
        List<Role> roles = findRoleService.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleResponseDTO.class))
                .toList();
    }
}
