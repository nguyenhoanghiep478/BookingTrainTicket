package com.backend.store.application.usecase.Schedule;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.ScheduleOutOfTimeException;
import com.backend.store.core.domain.exception.StationNotInSchedule;
import com.backend.store.core.domain.repository.IScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindScheduleUseCase {
    private final IScheduleRepository scheduleRepository;
    private final FindStationUseCase findStationUseCase;

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

    public List<Schedule> findInIds(List<Integer> ids){
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation("IN")
                .value(ids)
                .build();
        return scheduleRepository.findBy(List.of(criteria));
    }

    public List<Schedule> findRoundTrip(Integer departureStationId, Integer arrivalStationId, Integer scheduleId) {
        Schedule schedule = findById(scheduleId);
        Station departureStation = findStationUseCase.getStationById(departureStationId);
        Station arrivalStation = findStationUseCase.getStationById(arrivalStationId);
        Timestamp arrivalTime = schedule.getScheduleStations().stream().filter(station -> station.getStation().equals(arrivalStation)).findFirst().get().getArrivalTime();

        isInSchedule(schedule,departureStation,arrivalStation);
        List<Object[]> objects = scheduleRepository.findReturnTrip(departureStationId,arrivalStationId,arrivalTime);
        List<Integer> ids = new ArrayList<>();
        for(Object[] object: objects){
            Integer id = (Integer) object[0];
            ids.add(id);
        }
        return findInIds(ids);
    }

    private void isInSchedule(Schedule schedule, Station departureStation, Station arrivalStation){
        List<ScheduleStation> scheduleStations = schedule.getScheduleStations();
        List<Station> stations = scheduleStations.stream().map(ScheduleStation::getStation).toList();
        if(!stations.contains(departureStation) || !stations.contains(arrivalStation)) {
            throw new StationNotInSchedule(String.format("Station %s or Station %s  not in schedule ", departureStation.getName(), arrivalStation.getName()));
        }
    }
}
