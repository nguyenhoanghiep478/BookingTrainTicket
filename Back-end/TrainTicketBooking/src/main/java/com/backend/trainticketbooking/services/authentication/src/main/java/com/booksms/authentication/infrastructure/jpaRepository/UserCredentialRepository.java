package com.booksms.authentication.infrastructure.jpaRepository;

import com.booksms.authentication.core.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByEmail(String email);

}
