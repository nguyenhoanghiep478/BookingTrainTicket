package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.interfaceLayer.DTO.Response.RoleResponseDTO;

import java.util.List;

public interface IRoleService {
    List<RoleResponseDTO> findAll();
}
