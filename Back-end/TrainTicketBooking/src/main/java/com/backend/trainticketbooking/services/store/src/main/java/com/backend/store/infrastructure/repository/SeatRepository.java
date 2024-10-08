package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.repository.ISeatRepository;
import com.backend.store.infrastructure.jpaRepository.SeatJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SeatRepository extends AbstractRepository<Seat> implements ISeatRepository {
    private final SeatJpaRepository seatJpaRepository;

    SeatRepository(Class<Seat> entityClass, SeatJpaRepository seatJpaRepository) {
        super(entityClass);
        this.seatJpaRepository = seatJpaRepository;
    }

    @Override
    public Optional<Seat> findById(Integer id) {
        return seatJpaRepository.findById(id);
    }

    @Override
    public Seat save(Seat afterMerge) {
        return seatJpaRepository.save(afterMerge);
    }

    @Override
    public List<Seat> findAll() {
        return seatJpaRepository.findAll();
    }

    @Override
    public List<Seat> findBy(List<Criteria> criteria) {
        return abstractSearch(criteria);
    }
}
