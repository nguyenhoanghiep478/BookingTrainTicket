package com.backend.store.application.usecase.Railcar;

import com.backend.store.application.model.RailcarModel;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.RailcarInUseException;
import com.backend.store.core.domain.exception.RailcarNotExistException;
import com.backend.store.core.domain.repository.IRailcarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UpdateRailcarUseCase {
    private final IRailcarRepository railcarRepository;


    public Railcar execute(RailcarModel model, Train train) {
        Railcar railcar = railcarRepository.findById(model.getId()).orElseThrow(
                () -> new RailcarNotExistException(String.format("Railcar with id %s not found", model.getId()))
        );
        if(railcar.getTrain() != null){
            throw new RailcarInUseException(String.format("Railcar with id %s already in use", model.getId()));
        }
        Railcar afterMerge = merge(model, railcar, train);

        afterMerge.getSeats().forEach(seat -> {
            String[] parts = seat.getSeatNumber().split("\\.");
            String numberPart = parts.length > 1 ? parts[1] : "";
            seat.setSeatNumber(String.format("%s.%s", railcar.getName(), numberPart));
        });

        return railcarRepository.save(afterMerge);
    }




    private Railcar merge(RailcarModel model , Railcar railcar,Train train) {
        if(model.getName() != null){
            railcar.setName(model.getName());
        }
        if(train != null){
            railcar.setTrain(train);
        }
        if(model.getIsHaveFloor() != null){
            railcar.setIsHaveFloor(model.getIsHaveFloor());
        }
        if(model.getCapacity() != null){
            railcar.setCapacity(model.getCapacity());
        }
        if(model.getSeatPerRow() != null){
            railcar.setSeatPerRow(model.getSeatPerRow());
        }
        return railcar;
    }
}
