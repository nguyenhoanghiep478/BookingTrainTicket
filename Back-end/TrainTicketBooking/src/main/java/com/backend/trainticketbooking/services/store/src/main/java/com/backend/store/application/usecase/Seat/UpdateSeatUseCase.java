package com.backend.store.application.usecase.Seat;

import com.backend.store.application.model.SeatModel;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.exception.SeatNotExistException;
import com.backend.store.core.domain.repository.ISeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateSeatUseCase {
    private final ISeatRepository seatRepository;

    public Seat execute(SeatModel model){
        Seat seat =  seatRepository.findById(model.getId()).orElseThrow(
                () -> new SeatNotExistException(String.format("Seat with id %s not found", model.getId()))
        );

        Seat afterMerge = merge(seat, model);

        return seatRepository.save(afterMerge);
    }

    private Seat merge(Seat seat,SeatModel model){
        if(model.getPrice() != null){
            seat.setPrice(model.getPrice());
        }
        if(model.getIsAvailable() != null){
            seat.setIsAvailable(model.getIsAvailable());
        }
        return seat;
    }
}
