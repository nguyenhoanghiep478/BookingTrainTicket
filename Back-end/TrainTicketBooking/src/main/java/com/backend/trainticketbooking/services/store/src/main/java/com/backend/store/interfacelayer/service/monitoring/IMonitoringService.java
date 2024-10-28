package com.backend.store.interfacelayer.service.monitoring;

import com.backend.store.interfacelayer.dto.request.MonitoringRequest;
import com.backend.store.interfacelayer.dto.response.MonitoringResponse;

public interface IMonitoringService {
    MonitoringResponse updateScheduleOfTrain(MonitoringRequest request);
}
