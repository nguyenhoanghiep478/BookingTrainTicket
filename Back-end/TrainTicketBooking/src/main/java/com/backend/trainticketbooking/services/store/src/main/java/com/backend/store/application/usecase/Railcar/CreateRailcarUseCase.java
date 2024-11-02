package com.backend.store.application.usecase.Railcar;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.model.RailcarModel;
import com.backend.store.application.usecase.Train.FindTrainUseCase;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.InvalidAmountOfSeatPerRailcar;
import com.backend.store.core.domain.exception.RailcarExistedException;
import com.backend.store.core.domain.exception.TrainNotExistedException;
import com.backend.store.core.domain.repository.IRailcarRepository;
import com.backend.store.core.domain.state.StaticVar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.backend.store.core.domain.state.StaticVar.*;

@Component
@RequiredArgsConstructor
public class CreateRailcarUseCase {
    private final IRailcarRepository railcarRepository;
    private final FindTrainUseCase findTrainUseCase;

    @Transactional(rollbackFor = Exception.class)
    public Railcar execute(final RailcarModel model) {
        railcarRepository.findByName(model.getName()).ifPresent( railcar -> {
                    throw new RailcarExistedException(String.format("Railcar with name %s already exists", model.getName()));
        });

        Railcar railcar = map(model);

        return railcarRepository.save(railcar);
    }

    private Railcar map(final RailcarModel model) {
        Railcar railcar = new Railcar();
        if(model.getTrainId() != null){
            Criteria findById = Criteria.builder()
                    .key("id")
                    .operation(":")
                    .value(model.getTrainId())
                    .build();

            Train train = findTrainUseCase.execute(List.of(findById)).get(0);
            if(train == null){
                throw new TrainNotExistedException(String.format("Train with id %s does not exist", model.getTrainId()));
            }
            railcar.setTrain(train);
        }

        railcar.setName(model.getName());
        railcar.setRailcarType(model.getRailcarType());
        if(model.getCapacity() > MAXIMUM_SEAT_PER_RAILCAR){
            throw new InvalidAmountOfSeatPerRailcar(String.format("Maximum seat per railcar must less than or equal %s",MAXIMUM_SEAT_PER_RAILCAR));
        }
        railcar.setCapacity(model.getCapacity());
        railcar.setIsHaveFloor(model.getIsHaveFloor());
        railcar.setSeatPerRow(model.getSeatPerRow());

        return railcar;
    }
}
