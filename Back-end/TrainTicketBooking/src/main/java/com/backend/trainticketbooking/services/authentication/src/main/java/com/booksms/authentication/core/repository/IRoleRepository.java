package com.booksms.authentication.core.repository;

import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.core.entity.Role;

import java.util.List;

public interface IRoleRepository {
    List<Role> findAll();

    List<Role> findByCriteria(List<SearchUserCriteria> criteriaList);
}
