package com.backend.store.application.usecase.Schedule;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.repository.IScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindScheduleUseCase {
    private final IScheduleRepository scheduleRepository;
    public List<Schedule> execute(List<Criteria> criteria){
        if(criteria == null || criteria.isEmpty()){
            return scheduleRepository.findAll();
        }
        return scheduleRepository.findBy(criteria);
    }

    public Schedule findById(int id){
        return scheduleRepository.findById(id);
    }



}
