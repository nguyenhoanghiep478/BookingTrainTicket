package com.booksms.authentication.core.repository;

import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.core.entity.Permission;
import com.booksms.authentication.core.entity.UserCredential;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserRepository {
    Optional<UserCredential> findByEmail(String email);

    Optional<UserCredential> save(UserCredential credential);

    Set<Permission> findPermissionById(Integer id);

    List<UserCredential> search(List<SearchUserCriteria> criteriaList);

    List<UserCredential> findAll();
}
