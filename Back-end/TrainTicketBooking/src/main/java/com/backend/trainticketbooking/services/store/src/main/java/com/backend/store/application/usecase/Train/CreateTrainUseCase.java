package com.backend.store.application.usecase.Train;

import com.backend.store.application.model.TrainModel;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.TrainExistedException;
import com.backend.store.core.domain.repository.ITrainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTrainUseCase {
    private final ITrainRepository trainRepository;

    @Transactional(rollbackOn = Exception.class)
    public Train execute(TrainModel trainModel){
        Train train = trainRepository.findByTrainNameOrTrainNumber(trainModel.getTrainName(),trainModel.getTrainName());

        if(train != null){
            throw new TrainExistedException(String.format("Train name %s or train number %s already existed", trainModel.getTrainName(),trainModel.getTrainNumber()));
        }

        Train createTrain = map(trainModel);

        return trainRepository.save(createTrain);
    }

    private Train map(TrainModel trainModel){
        Train train = new Train();
        train.setTrainName(trainModel.getTrainName());
        train.setTrainNumber(trainModel.getTrainNumber());
        train.setTrainType(trainModel.getTrainType());
        train.setTotalRailCars(0);
        train.setCapacity(0);
        return train;
    }
}
