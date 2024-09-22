package com.backend.store.application.usecase.Train;

import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.RailcarInUseException;
import com.backend.store.core.domain.exception.TrainNotExistedException;
import com.backend.store.core.domain.repository.ITrainRepository;
import com.backend.store.core.domain.state.RailcarType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class AddRailcarUseCase {
    private final ITrainRepository trainRepository;

    public Train execute(Train train,List<Railcar> addRailcars) throws TrainNotExistedException {

        Set<Railcar> railcars = train.getRailcars();
        AtomicReference<Integer> capacity = new AtomicReference<>(train.getCapacity());

        addRailcars.forEach(railcar -> {
            if(railcar.getTrain() != null){
                throw new RailcarInUseException(String.format("Railcar %s is already in use", railcar.getTrain().getId()));
            }
            if( !railcar.getRailcarType().equals(RailcarType.DINNING_CAR)){
                railcar.getSeats().forEach(seat -> seat.setIsAvailable(true));
            }
            capacity.set(capacity.get() + railcar.getCapacity());
            railcar.setTrain(train);
            railcars.add(railcar);
        });

        train.setTotalRailCars(train.getRailcars().size());
        train.setCapacity(capacity.get());

        return trainRepository.save(train);
    }
}
