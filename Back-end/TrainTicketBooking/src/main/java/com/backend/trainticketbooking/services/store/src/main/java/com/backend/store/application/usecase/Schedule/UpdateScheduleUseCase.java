package com.backend.store.application.usecase.Schedule;

import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.TrainNotAvailableException;
import com.backend.store.core.domain.repository.IScheduleRepository;
import com.backend.store.core.domain.repository.IScheduleStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.*;
import java.util.List;

import static com.backend.store.core.domain.state.StaticVar.BREAK_TIME_MINUTES;
import static com.backend.store.core.domain.state.StaticVar.TRAVEL_TIME_MINUTES;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateScheduleUseCase {
     private final IScheduleRepository scheduleRepository;
     private final IScheduleStationRepository scheduleStationRepository;
     public void updateByActualDepartureTime(Schedule schedule, Station currentStation) {
         Train train = schedule.getTrain();
         ScheduleStation currentScheduleStation = schedule.getScheduleStations().stream().filter(scheduleStation -> scheduleStation.getStation().equals(currentStation)).findAny().get();
         if(train.getCapacity() == 0 ){
             throw new TrainNotAvailableException(String.format("Train %s not available because capacity is 0" , train.getTrainName()));
         }

        for(ScheduleStation scheduleStation : schedule.getScheduleStations()) {
            LocalDateTime actualDepartureTime = LocalDateTime.now();
            log.info(actualDepartureTime.toString());
            ZonedDateTime zonedDateTime = actualDepartureTime.atZone(ZoneId.systemDefault());

            Instant instant = zonedDateTime.toInstant();
            final Timestamp[] startTime = {Timestamp.from(instant)};
            if(scheduleStation.getStopSequence() < currentScheduleStation.getStopSequence()) {
                continue;
            }

            if(scheduleStation.getStation().equals(currentStation)){
                scheduleStation.setActualDepartureTime(Timestamp.valueOf(actualDepartureTime));
                scheduleStationRepository.save(scheduleStation);
                continue;
            }

            LocalDateTime departureTime = actualDepartureTime.plusMinutes(TRAVEL_TIME_MINUTES);
            LocalDateTime arrivalTime = departureTime.plusMinutes(BREAK_TIME_MINUTES);

            Timestamp arrivalTimestamp = Timestamp.valueOf(arrivalTime);
            Timestamp departureTimestamp = Timestamp.valueOf(departureTime);

            scheduleStation.setDepartureTime(departureTimestamp);
            scheduleStation.setArrivalTime(arrivalTimestamp);

            long travelTimeMillis = Duration.between(actualDepartureTime, arrivalTime).toMillis();
            scheduleStation.setTravelTime(travelTimeMillis);

            actualDepartureTime = arrivalTime;

            startTime[0] = Timestamp.valueOf(actualDepartureTime);


            scheduleStationRepository.save(scheduleStation);
        }

         scheduleRepository.save(schedule);
     }
}
