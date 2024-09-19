package com.backend.store.interfacelayer.service.schedule.impl;

import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.dto.response.CreateScheduleResponse;
import com.backend.store.interfacelayer.service.schedule.ICreateScheduleService;
import com.backend.store.interfacelayer.service.schedule.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
    private final ICreateScheduleService createScheduleService;

    @Override
    public CreateScheduleResponse create(CreateScheduleRequest request) {
        Schedule schedule = createScheduleService.create(request);
        return CreateScheduleResponse.builder()
                .routeName(schedule.getRoute().getName())
                .trainName(schedule.getRoute().getName())
                .build();
    }
}
