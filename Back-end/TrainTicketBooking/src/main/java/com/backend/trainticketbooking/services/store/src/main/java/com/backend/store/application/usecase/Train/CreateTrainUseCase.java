package com.backend.store.application.usecase.Train;

import com.backend.store.application.model.TrainModel;
import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.TrainExistedException;
import com.backend.store.core.domain.repository.ITrainRepository;
import com.backend.store.core.domain.state.TrainStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static com.backend.store.core.domain.state.TrainStatus.*;



@Component
@RequiredArgsConstructor
public class CreateTrainUseCase {
    private final ITrainRepository trainRepository;
    private final FindStationUseCase findStationUseCase;

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
        train.setTrainStatus(ON_NOT_WORKING);
        train.setCurrentStation(findStationUseCase.getStationById(trainModel.getCurrentStationId()));
        train.setTotalRailCars(0);
        train.setCapacity(0);
        return train;
    }
}
