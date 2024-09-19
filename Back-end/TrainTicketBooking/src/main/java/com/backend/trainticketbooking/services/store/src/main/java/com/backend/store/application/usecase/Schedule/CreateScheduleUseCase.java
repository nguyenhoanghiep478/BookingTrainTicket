package com.backend.store.application.usecase.Schedule;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.model.ScheduleModel;
import com.backend.store.application.usecase.Route.FindRouteUseCase;
import com.backend.store.application.usecase.Train.FindTrainUseCase;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.ScheduleExistedException;
import com.backend.store.core.domain.repository.IScheduleRepository;
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
                Optional<Schedule> schedule = repository.findByRouteIdAndTrainId(scheduleModel.getRouteId(),scheduleModel.getTrainId());
                if(schedule.isPresent()) {
                        throw new ScheduleExistedException(String.format("Schedule with train id %s and route id %s already exists", scheduleModel.getTrainId(),scheduleModel.getRouteId()));
                }
                Schedule newSchedule = map(scheduleModel);

                return repository.save(newSchedule);
        }

        private Schedule map(ScheduleModel scheduleModel) {
                Route route = findRouteUseCase.findById(scheduleModel.getRouteId());
                Train train = findTrainUseCase.findById(scheduleModel.getTrainId());

                Schedule schedule = new Schedule();
                LocalDate tomorrow = LocalDate.now().plusDays(1);

                LocalTime nineAM = LocalTime.of(9, 0);

                LocalDateTime dateTime = LocalDateTime.of(tomorrow, nineAM);

                ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());

                Instant instant = zonedDateTime.toInstant();
                final Timestamp[] startTime = {Timestamp.from(instant)};

                long travelTimeMinutes = 60;
                long breakTimeMinutes = 20;

                List<ScheduleStation> scheduleStations = route.getRouteStations().stream()
                        .map(routeStation -> {
                                ScheduleStation scheduleStation = new ScheduleStation();
                                scheduleStation.setSchedule(schedule);
                                scheduleStation.setStation(routeStation.getStation());

                                LocalDateTime startLocalDateTime = startTime[0].toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                                LocalDateTime arrivalTime = startLocalDateTime.plusMinutes(travelTimeMinutes);
                                LocalDateTime departureTime = arrivalTime.plusMinutes(breakTimeMinutes);

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
