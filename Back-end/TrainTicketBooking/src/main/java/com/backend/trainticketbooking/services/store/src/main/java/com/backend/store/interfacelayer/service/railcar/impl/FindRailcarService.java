package com.backend.store.interfacelayer.service.railcar.impl;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.usecase.Railcar.FindRailcarUseCase;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.interfacelayer.service.railcar.IFindRailcarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindRailcarService implements IFindRailcarService {
    private final FindRailcarUseCase findRailcarUseCase;
    @Override
    public List<Railcar> getAll() {
        return findRailcarUseCase.execute(null);
    }

    @Override
    public Railcar getById(int id) {
        Criteria criteria = Criteria.builder()
                .key("id")
                .value(id)
                .operation(":")
                .build();
        return findRailcarUseCase.execute(List.of(criteria)).get(0);
    }
}
