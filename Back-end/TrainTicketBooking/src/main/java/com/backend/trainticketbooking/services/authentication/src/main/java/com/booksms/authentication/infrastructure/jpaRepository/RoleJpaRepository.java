package com.booksms.authentication.infrastructure.jpaRepository;

import com.booksms.authentication.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Integer> {
}
