package com.backend.store.application.usecase.Schedule;

import com.backend.store.application.model.ScheduleModel;
import com.backend.store.application.usecase.Route.FindRouteUseCase;
import com.backend.store.application.usecase.Train.FindTrainUseCase;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.ScheduleExistedException;
import com.backend.store.core.domain.exception.TrainNotAvailableException;
import com.backend.store.core.domain.repository.IScheduleRepository;
import static com.backend.store.core.domain.state.StaticVar.*;

import com.backend.store.core.domain.state.StaticVar;
import com.backend.store.core.domain.state.TrainStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.*;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateScheduleUseCase {
        private final IScheduleRepository repository;
        private final FindRouteUseCase findRouteUseCase;
        private final FindTrainUseCase findTrainUseCase;

        public Schedule execute(final ScheduleModel scheduleModel) {
                Train train = findTrainUseCase.findById(scheduleModel.getTrainId());
                if(!train.getTrainStatus().equals(TrainStatus.ON_NOT_WORKING)){
                    throw new TrainNotAvailableException(String.format("Train %s is running in another schedule",train.getTrainName()));
                }

                Route route = findRouteUseCase.findById(scheduleModel.getRouteId());
                Station startStation = route.getRouteStations().get(0).getStation();

                if(!train.getCurrentStation().equals(startStation) ){
                    throw new TrainNotAvailableException(String.format("Train %s is not at the current station",train.getTrainName()));
                }

                Schedule newSchedule = map(scheduleModel);
                train.setCurrentStation(startStation);
                return repository.save(newSchedule);
        }

        private Schedule map(ScheduleModel scheduleModel) {
                Route route = findRouteUseCase.findById(scheduleModel.getRouteId());
                Train train = findTrainUseCase.findById(scheduleModel.getTrainId());

                if(train.getCapacity() == 0 ){
                    throw new TrainNotAvailableException(String.format("Train %s not available because capacity is 0" , train.getTrainName()));
                }

                Schedule schedule = new Schedule();
                LocalDate tomorrow = LocalDate.now().plusDays(1);

                LocalTime nineAM = LocalTime.of(9, 0);

                LocalDateTime dateTime = LocalDateTime.of(tomorrow, nineAM);

                ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());

                Instant instant = zonedDateTime.toInstant();
                final Timestamp[] startTime = {Timestamp.from(instant)};


                List<ScheduleStation> scheduleStations = route.getRouteStations().stream()
                        .map(routeStation -> {
                                ScheduleStation scheduleStation = new ScheduleStation();
                                scheduleStation.setSchedule(schedule);
                                scheduleStation.setStation(routeStation.getStation());

                                LocalDateTime startLocalDateTime = startTime[0].toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                                LocalDateTime departureTime = startLocalDateTime.plusMinutes(TRAVEL_TIME_MINUTES);
                                LocalDateTime arrivalTime = departureTime.plusMinutes(BREAK_TIME_MINUTES);

                                Timestamp arrivalTimestamp = Timestamp.valueOf(arrivalTime);
                                Timestamp departureTimestamp = Timestamp.valueOf(departureTime);

                                scheduleStation.setDepartureTime(departureTimestamp);
                                scheduleStation.setArrivalTime(arrivalTimestamp);
                                scheduleStation.setStopSequence(routeStation.getStopOrder());

                                long travelTimeMillis = Duration.between(startLocalDateTime, arrivalTime).toMillis();
                                scheduleStation.setTravelTime(travelTimeMillis);

                                startLocalDateTime = departureTime;

                                startTime[0] = Timestamp.valueOf(startLocalDateTime);

                                return scheduleStation;
                        })
                        .toList();

                schedule.setScheduleStations(scheduleStations);
                schedule.setRoute(route);
                schedule.setTrain(train);

                route.getSchedules().add(schedule);

                return schedule;
        }


}
