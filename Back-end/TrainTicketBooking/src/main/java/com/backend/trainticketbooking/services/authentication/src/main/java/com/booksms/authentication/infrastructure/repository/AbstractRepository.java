package com.booksms.authentication.infrastructure.repository;

import com.booksms.authentication.application.model.SearchUserCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class AbstractRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> abstractSearch(List<SearchUserCriteria> searchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        for(SearchUserCriteria criteria : searchCriteria) {
            switch (criteria.getOperation()){
                case ":":
                    predicates.add(builder.equal(root.get(criteria.getKey()),(Comparable) criteria.getValue()));
                    break;
                case ">":
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()),(Comparable) criteria.getValue()));
                    break;
                case "<":
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                    break;
                case "LIKE":
                    predicates.add(builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                    break;
                case "IN":
                    if(criteria.getValue() instanceof List<?>){
                        List<Integer> ids = ((List<?>) criteria.getValue())
                                .stream()
                                .filter(item -> item instanceof Integer)
                                .map(item -> (Integer) item)
                                .toList();

                        predicates.add(root.get(criteria.getKey()).in(ids));
                    }
                    break;
            }
        }
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
