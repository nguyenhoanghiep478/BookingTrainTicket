package com.backend.store.interfacelayer.service.schedule.impl;

import com.backend.store.application.usecase.Schedule.FindScheduleUseCase;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.interfacelayer.service.schedule.IFindScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FindScheduleService implements IFindScheduleService {
    private final FindScheduleUseCase findScheduleUseCase;

    @Override
    public Schedule findById(int id) {
        return findScheduleUseCase.findById(id);
    }


}
