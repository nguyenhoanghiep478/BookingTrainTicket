package com.booksms.authentication.infrastructure.repository;

import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.core.entity.Permission;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.repository.IUserRepository;
import com.booksms.authentication.infrastructure.jpaRepository.UserCredentialRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository extends AbstractRepository<UserCredential> implements IUserRepository {
    private final UserCredentialRepository repository;
    @PersistenceContext
    private EntityManager entityManager;
    UserRepository(Class<UserCredential> entityClass, UserCredentialRepository repository) {
        super(entityClass);
        this.repository = repository;
    }

    @Override
    public Optional<UserCredential> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<UserCredential> save(UserCredential credential) {
        return Optional.of(repository.save(credential));
    }

    @Override
    public Set<Permission> findPermissionById(Integer id) {
      String jpql = "select p from Permission p JOIN p.roles r JOIN r.users u where u.id = :userId";
      List<Permission> permissions = entityManager.createQuery(jpql, Permission.class)
              .setParameter("userId",id)
              .getResultList();
      return new HashSet<>(permissions);
    }

    @Override
    public List<UserCredential> search(List<SearchUserCriteria> criteriaList) {
       return abstractSearch(criteriaList);
    }

    @Override
    public List<UserCredential> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteByUser(UserCredential userCredential) {
        repository.delete(userCredential);
    }
}
