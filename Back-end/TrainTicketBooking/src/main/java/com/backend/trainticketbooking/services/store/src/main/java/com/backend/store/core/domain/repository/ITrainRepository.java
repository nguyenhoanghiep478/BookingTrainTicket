package com.backend.store.core.domain.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Train;

import java.util.List;
import java.util.Optional;

public interface ITrainRepository {
    List<Train> findBy(List<Criteria> criteriaList);

    List<Train> findAll();

    Train findByTrainNameOrTrainNumber(String trainName, String trainName1);

    Train save(Train createTrain);

    Optional<Train> findById(Integer id);
}
