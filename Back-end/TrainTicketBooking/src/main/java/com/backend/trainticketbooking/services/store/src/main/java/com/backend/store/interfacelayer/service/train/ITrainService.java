package com.backend.store.interfacelayer.service.train;

import com.backend.store.interfacelayer.dto.request.ModifiedRailcarsToTrainRequest;
import com.backend.store.interfacelayer.dto.request.CreateTrainRequest;
import com.backend.store.interfacelayer.dto.response.ModifiedRailcarsToTrainResponse;
import com.backend.store.interfacelayer.dto.response.CreateTrainResponse;

public interface ITrainService {
    CreateTrainResponse create(CreateTrainRequest request);

    ModifiedRailcarsToTrainResponse addRailcars(ModifiedRailcarsToTrainRequest request);

    ModifiedRailcarsToTrainResponse detachRailcars(ModifiedRailcarsToTrainRequest request);
}
