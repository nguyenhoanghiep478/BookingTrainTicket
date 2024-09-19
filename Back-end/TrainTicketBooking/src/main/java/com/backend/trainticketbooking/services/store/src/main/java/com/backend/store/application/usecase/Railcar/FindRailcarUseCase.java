package com.backend.store.application.usecase.Railcar;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.repository.IRailcarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindRailcarUseCase {
    private final IRailcarRepository railcarRepository;

    public List<Railcar> execute(List<Criteria> criteria) {
        if(criteria == null || criteria.isEmpty()){
            return railcarRepository.findAll();
        }
        return railcarRepository.findBy(criteria);
    }
}
