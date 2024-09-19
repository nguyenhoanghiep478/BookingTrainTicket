package com.backend.store.interfacelayer.service.schedule;

import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.dto.response.CreateScheduleResponse;

public interface IScheduleService {
    CreateScheduleResponse create(CreateScheduleRequest request);
}
