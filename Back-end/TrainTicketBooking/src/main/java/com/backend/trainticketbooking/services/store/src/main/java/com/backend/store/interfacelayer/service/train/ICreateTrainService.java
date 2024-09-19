package com.backend.store.interfacelayer.service.train;

import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.dto.request.CreateTrainRequest;

public interface ICreateTrainService {
    Train create(CreateTrainRequest request);
}
