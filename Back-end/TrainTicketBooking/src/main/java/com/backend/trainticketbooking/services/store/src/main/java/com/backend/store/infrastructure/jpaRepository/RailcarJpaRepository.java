package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.train.Railcar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RailcarJpaRepository extends JpaRepository<Railcar, Integer> {
    Optional<Railcar> findByName(String name);
}
