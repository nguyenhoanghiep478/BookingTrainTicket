package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Railcar;

import java.util.List;
import java.util.Optional;

public interface IRailcarRepository {
    Optional<Railcar> findByName(String name);

    Railcar save(Railcar railcar);

    List<Railcar> findAll();

    List<Railcar> findBy(List<Criteria> criteria);

    Optional<Railcar> findById(Integer id);
}
