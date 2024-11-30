package com.backend.store.interfacelayer.service.train.impl;

import com.backend.store.application.usecase.Train.FindTrainUseCase;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.service.train.IFindTrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindTrainService implements IFindTrainService {
    private final FindTrainUseCase findTrainUseCase;

    @Override
    public List<Train> getAll() {
        return findTrainUseCase.execute(null);
    }

    @Override
    public Train getById(int id) {
        return findTrainUseCase.findById(id);
    }
}
