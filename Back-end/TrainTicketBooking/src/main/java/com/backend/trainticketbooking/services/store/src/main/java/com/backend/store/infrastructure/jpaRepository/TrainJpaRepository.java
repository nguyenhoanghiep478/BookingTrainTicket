package com.backend.store.infrastructure.jpaRepository;

import com.backend.store.core.domain.entity.train.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainJpaRepository extends JpaRepository<Train, Integer> {
     Train getTrainByTrainNameOrTrainNumber(String trainName, String trainNumber);
}
