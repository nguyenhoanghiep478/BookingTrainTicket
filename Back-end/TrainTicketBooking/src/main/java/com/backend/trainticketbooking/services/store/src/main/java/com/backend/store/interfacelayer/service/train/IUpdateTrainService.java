package com.backend.store.interfacelayer.service.train;

import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.dto.request.ModifiedRailcarsToTrainRequest;

public interface IUpdateTrainService {
    Train addRailcarsToTrain(ModifiedRailcarsToTrainRequest request);

    Train detachRailcars(ModifiedRailcarsToTrainRequest request);
}
