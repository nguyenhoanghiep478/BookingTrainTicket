package com.backend.store.application.usecase.Schedule;

import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.TrainNotAvailableException;
import com.backend.store.core.domain.repository.IScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.*;

import static com.backend.store.core.domain.state.StaticVar.BREAK_TIME_MINUTES;
import static com.backend.store.core.domain.state.StaticVar.TRAVEL_TIME_MINUTES;

@Component
@RequiredArgsConstructor
public class UpdateScheduleUseCase {
     private final IScheduleRepository scheduleRepository;

     public Schedule updateByActualDepartureTime(Schedule schedule, Station currentStation) {
         Train train = schedule.getTrain();

         if(train.getCapacity() == 0 ){
             throw new TrainNotAvailableException(String.format("Train %s not available because capacity is 0" , train.getTrainName()));
         }


         schedule.getScheduleStations().stream().map(scheduleStation -> {
             LocalDateTime actualDepartureTime = LocalDateTime.now();
             ZonedDateTime zonedDateTime = actualDepartureTime.atZone(ZoneId.systemDefault());

             Instant instant = zonedDateTime.toInstant();
             final Timestamp[] startTime = {Timestamp.from(instant)};

             if(scheduleStation.getStation().equals(currentStation)){
                 scheduleStation.setActualDepartureTime(Timestamp.valueOf(actualDepartureTime));

                return scheduleStation;
             }

             LocalDateTime arrivalTime = actualDepartureTime.plusMinutes(TRAVEL_TIME_MINUTES);
             LocalDateTime departureTime = arrivalTime.plusMinutes(BREAK_TIME_MINUTES);

             Timestamp arrivalTimestamp = Timestamp.valueOf(arrivalTime);
             Timestamp departureTimestamp = Timestamp.valueOf(departureTime);

             scheduleStation.setDepartureTime(departureTimestamp);
             scheduleStation.setArrivalTime(arrivalTimestamp);

             long travelTimeMillis = Duration.between(actualDepartureTime, arrivalTime).toMillis();
             scheduleStation.setTravelTime(travelTimeMillis);

             actualDepartureTime = departureTime;

             startTime[0] = Timestamp.valueOf(actualDepartureTime);

             return scheduleStation;
         });

         return scheduleRepository.save(schedule);
     }
}
