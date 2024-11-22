package com.backend.store.interfacelayer.service.train;

import com.backend.store.core.domain.entity.train.Train;

import java.util.List;

public interface IFindTrainService {
    List<Train> getAll();
}
