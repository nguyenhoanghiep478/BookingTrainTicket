package com.backend.store.infrastructure.repository;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.repository.ITrainRepository;
import com.backend.store.infrastructure.jpaRepository.TrainJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainRepository extends AbstractRepository<Train> implements ITrainRepository {
    private final TrainJpaRepository trainJpaRepository;
    TrainRepository(Class<Train> entityClass, TrainJpaRepository trainJpaRepository) {
        super(entityClass);
        this.trainJpaRepository = trainJpaRepository;
    }

    @Override
    public List<Train> findBy(List<Criteria> criteriaList) {
        return abstractSearch(criteriaList);
    }

    @Override
    public List<Train> findAll() {
        return trainJpaRepository.findAll();
    }

    @Override
    public Train findByTrainNameOrTrainNumber(String trainName, String trainNumber) {
        return trainJpaRepository.getTrainByTrainNameOrTrainNumber(trainName, trainNumber);
    }

    @Override
    public Train save(Train createTrain) {
        return trainJpaRepository.save(createTrain);
    }

    @Override
    public Optional<Train> findById(Integer id) {
        return trainJpaRepository.findById(id);
    }
}
