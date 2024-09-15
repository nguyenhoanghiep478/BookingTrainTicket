package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.core.entity.Role;

import java.util.List;

public interface IFindRoleService {
    List<Role> findAll();
}
