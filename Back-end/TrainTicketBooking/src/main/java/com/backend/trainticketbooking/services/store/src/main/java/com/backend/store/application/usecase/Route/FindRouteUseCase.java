package com.backend.store.application.usecase.Route;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.repository.IRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindRouteUseCase {
    private final IRouteRepository routeRepository;

    public List<Route> execute(List<Criteria> criteria) {
        if(criteria == null || criteria.isEmpty()){
            return routeRepository.findAll();
        }
        return routeRepository.findBy(criteria);
    }

    public Route findById(Integer routeId) {
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation(":")
                .value(routeId)
                .build();

        return execute(List.of(criteria)).get(0);
    }
}
