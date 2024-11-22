package com.backend.store.application.usecase.Schedule;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.ScheduleOutOfTimeException;
import com.backend.store.core.domain.repository.IScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    public void validate(Schedule schedule, Station departureStation){
        ScheduleStation scheduleStation = schedule.getScheduleStations().stream().filter(finding -> finding.getStation().equals(departureStation)).findFirst().get();
        if (scheduleStation.getDepartureTime().before(new Date())){
            throw new ScheduleOutOfTimeException("Schedule time is out of time");
        }
        Train train = schedule.getTrain();
        ScheduleStation currentTrainStation = schedule.getScheduleStations().stream().filter(finding -> finding.getStation().equals(train.getCurrentStation())).findFirst().get();

        if(currentTrainStation.getStopSequence() > scheduleStation.getStopSequence()){
            throw new ScheduleOutOfTimeException(String.format("Train went over station %s ",departureStation.getName()));
        }
    }

}
