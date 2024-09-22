package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Seat;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ISeatRepository {
    Optional<Seat> findById(Integer id);

    Seat save(Seat afterMerge);

    List<Seat> findAll();

    List<Seat> findBy(List<Criteria> criteria);

    List<Objects> findByNativeQuery(String sql, List<Integer> ids);

    List<Integer> checkSeatsAtStation(List<Integer> seatIds,Integer scheduleId ,Integer stationId);

    List<Object[]> findTotalAvailableSeatAtStation(Integer scheduleId);
}
