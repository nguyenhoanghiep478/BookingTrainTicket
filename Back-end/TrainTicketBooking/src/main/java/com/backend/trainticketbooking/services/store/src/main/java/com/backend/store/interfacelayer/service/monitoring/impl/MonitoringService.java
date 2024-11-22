package com.backend.store.interfacelayer.service.monitoring.impl;

import com.backend.store.application.model.MonitoringModel;
import com.backend.store.application.usecase.monitoring.UpdateCurrentScheduleUseCase;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.interfacelayer.dto.request.MonitoringRequest;
import com.backend.store.interfacelayer.dto.response.MonitoringResponse;
import com.backend.store.interfacelayer.service.monitoring.IMonitoringService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringService implements IMonitoringService {
    private final UpdateCurrentScheduleUseCase updateCurrentScheduleUseCase;
    private final ModelMapper modelMapper;

    @Override
    public MonitoringResponse updateScheduleOfTrain(MonitoringRequest request) {
        return updateCurrentScheduleUseCase.execute(modelMapper.map(request, MonitoringModel.class));
    }
}
