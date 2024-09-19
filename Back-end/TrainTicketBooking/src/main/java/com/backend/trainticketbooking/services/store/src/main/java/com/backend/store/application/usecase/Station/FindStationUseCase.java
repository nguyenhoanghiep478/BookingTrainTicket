package com.backend.store.application.usecase.Station;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.repository.IStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindStationUseCase {
    private final IStationRepository stationRepository;

    public List<Station> execute(List<Criteria> criteria){
        if(criteria == null || criteria.isEmpty()){
            return stationRepository.findAll();
        }
        return stationRepository.findBy(criteria);
    }


    public List<Station> getListStationInIds(List<Integer> ids){
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation("IN")
                .value(ids)
                .build();
        return execute(List.of(criteria));
    }
}
