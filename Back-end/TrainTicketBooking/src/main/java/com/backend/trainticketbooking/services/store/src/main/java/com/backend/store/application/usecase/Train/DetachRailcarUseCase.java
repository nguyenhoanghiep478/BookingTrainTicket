package com.backend.store.application.usecase.Train;

import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.repository.ITrainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class DetachRailcarUseCase {
    private final ITrainRepository trainRepository;
    public Train execute(Train train, List<Railcar> railcarList) {
        Set<Railcar> railcars = train.getRailcars();
        AtomicReference<Integer> capacity = new AtomicReference<>(train.getCapacity());

        railcarList.forEach(railcar -> {
            if(railcars.contains(railcar)) {
                railcar.setTrain(null);
                railcars.remove(railcar);
                capacity.getAndUpdate(v -> v - railcar.getCapacity());
            }
        });

        train.setTotalRailCars(railcars.size());
        train.setCapacity(capacity.get());

        return trainRepository.save(train);
    }
}
