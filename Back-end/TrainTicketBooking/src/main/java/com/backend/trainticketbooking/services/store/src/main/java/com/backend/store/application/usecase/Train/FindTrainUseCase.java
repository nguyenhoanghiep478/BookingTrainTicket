package com.backend.store.application.usecase.Train;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.repository.ITrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindTrainUseCase {
    private final ITrainRepository trainRepository;

    public List<Train> execute(List<Criteria> criteriaList) {
        if(criteriaList == null || criteriaList.isEmpty()){
            return trainRepository.findAll();
        }
        return trainRepository.findBy(criteriaList);
    }

    public Train findById(Integer trainId) {
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation(":")
                .value(trainId)
                .build();

        return execute(List.of(criteria)).get(0);
    }
}
