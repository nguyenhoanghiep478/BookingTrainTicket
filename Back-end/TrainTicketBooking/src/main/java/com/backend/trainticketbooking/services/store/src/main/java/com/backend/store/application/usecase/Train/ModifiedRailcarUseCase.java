package com.backend.store.application.usecase.Train;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.model.TrainModel;
import com.backend.store.application.usecase.Railcar.FindRailcarUseCase;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.RailcarNotExistException;
import com.backend.store.core.domain.exception.TrainNotExistedException;
import com.backend.store.core.domain.repository.ITrainRepository;
import com.backend.store.core.domain.state.ModifiedType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModifiedRailcarUseCase {
    private final ITrainRepository trainRepository;
    private final FindRailcarUseCase findRailcarUseCase;
    private final AddRailcarUseCase addRailcarUseCase;
    private final DetachRailcarUseCase detachRailcarUseCase;

    public Train execute(TrainModel trainModel , ModifiedType modifiedType) {
        Train train = trainRepository.findById(trainModel.getId()).orElseThrow(
                () -> new TrainNotExistedException(String.format("Train with id %s does not exist", trainModel.getId()))
        );

        Criteria findInIds = Criteria.builder()
                .key("id")
                .operation("IN")
                .value(trainModel.getRailcarIds().stream().toList())
                .build();

        List<Railcar> railcarList = findRailcarUseCase.execute(List.of(findInIds));

        if(railcarList.isEmpty()) {
            throw new RailcarNotExistException("railcar not found");
        }

        if(modifiedType.equals(ModifiedType.ADD)){
            return addRailcarUseCase.execute(train, railcarList);
        }
        return detachRailcarUseCase.execute(train,railcarList);
    }
}
