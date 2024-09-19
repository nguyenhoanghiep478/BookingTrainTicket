package com.backend.store.application.usecase.Seat;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.repository.ISeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindSeatUseCase {
    private final ISeatRepository seatRepository;

    public List<Seat> execute(List<Criteria> criteria){
        if(criteria == null || criteria.isEmpty()){
            return seatRepository.findAll();
        }
        return seatRepository.findBy(criteria);
    }
}
