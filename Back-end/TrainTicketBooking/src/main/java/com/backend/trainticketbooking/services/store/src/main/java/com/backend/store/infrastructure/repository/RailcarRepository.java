package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.repository.IRailcarRepository;
import com.backend.store.infrastructure.jpaRepository.RailcarJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RailcarRepository extends AbstractRepository<Railcar> implements IRailcarRepository {
    private final RailcarJpaRepository railcarJpaRepository;

    RailcarRepository(Class<Railcar> entityClass, RailcarJpaRepository railcarJpaRepository) {
        super(entityClass);
        this.railcarJpaRepository = railcarJpaRepository;
    }

    @Override
    public Optional<Railcar> findByName(String name) {
        return railcarJpaRepository.findByName(name);
    }

    @Override
    public Railcar save(Railcar railcar) {
        return railcarJpaRepository.save(railcar);
    }

    @Override
    public List<Railcar> findAll() {
        return railcarJpaRepository.findAll();
    }

    @Override
    public List<Railcar> findBy(List<Criteria> criteria) {
        return abstractSearch(criteria);
    }

    @Override
    public Optional<Railcar> findById(Integer id) {
        return railcarJpaRepository.findById(id);
    }
}
