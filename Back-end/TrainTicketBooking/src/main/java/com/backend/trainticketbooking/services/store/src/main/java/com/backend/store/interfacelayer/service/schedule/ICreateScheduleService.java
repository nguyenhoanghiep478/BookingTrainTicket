package com.backend.store.interfacelayer.service.schedule;

import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;

public interface ICreateScheduleService {
    Schedule create(CreateScheduleRequest request);
}
