package com.booksms.authentication.infrastructure.repository;

import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.core.repository.IRoleRepository;
import com.booksms.authentication.infrastructure.jpaRepository.RoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository extends AbstractRepository<Role> implements IRoleRepository {
    private final RoleJpaRepository roleJpaRepository;

    RoleRepository(Class<Role> entityClass, RoleJpaRepository roleJpaRepository) {
        super(entityClass);
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll();
    }

    @Override
    public List<Role> findByCriteria(List<SearchUserCriteria> criteriaList) {
        return abstractSearch(criteriaList);
    }
}
