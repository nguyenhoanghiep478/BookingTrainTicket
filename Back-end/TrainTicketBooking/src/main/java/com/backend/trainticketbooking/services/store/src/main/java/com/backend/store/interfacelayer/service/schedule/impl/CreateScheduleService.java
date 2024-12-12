package com.backend.store.interfacelayer.service.schedule.impl;

import com.backend.store.application.model.ScheduleModel;
import com.backend.store.application.usecase.Schedule.CreateScheduleUseCase;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.dto.request.CreateScheduleRequest;
import com.backend.store.interfacelayer.service.schedule.ICreateScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateScheduleService implements ICreateScheduleService {
    private final CreateScheduleUseCase createScheduleUseCase;
    private final ModelMapper modelMapper;


    @Override
    public Schedule create(CreateScheduleRequest request) {
        ScheduleModel scheduleModel = ScheduleModel.builder()
                .trainId(request.getTrainId())
                .routeId(request.getRouteId())
                .startTime(request.getStartTime())
                .build();
        return createScheduleUseCase.execute(scheduleModel);
    }
}
