package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.train.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<Seat, Integer> {
}
