package com.backend.store.application.usecase.Schedule;

import com.backend.store.application.model.ScheduleModel;
import com.backend.store.application.usecase.Route.FindRouteUseCase;
import com.backend.store.application.usecase.Train.FindTrainUseCase;
import com.backend.store.core.domain.entity.schedule.*;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.InvalidStopSequence;
import com.backend.store.core.domain.exception.ScheduleExistedException;
import com.backend.store.core.domain.exception.TrainNotAvailableException;
import com.backend.store.core.domain.repository.IScheduleRepository;
import static com.backend.store.core.domain.state.StaticVar.*;
import static com.backend.store.core.domain.state.TrainStatus.ON_WORKING;

import com.backend.store.core.domain.state.StaticVar;
import com.backend.store.core.domain.state.TrainStatus;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.*;
import java.util.Comparator;
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
                route.getRouteStations().sort((o1, o2) -> o1.getStopOrder() - o2.getStopOrder());
                Station startStation = route.getRouteStations().get(0).getStation();
                if(train.getCurrentStation() != null && !train.getCurrentStation().equals(startStation) ){
                    throw new TrainNotAvailableException(String.format("Train %s is not at the current station",train.getTrainName()));
                }

                Schedule newSchedule = map(scheduleModel);
                train.setCurrentStation(startStation);
                train.setTrainStatus(ON_WORKING);
                return repository.save(newSchedule);
        }

        private Schedule map(ScheduleModel scheduleModel) {
                Route route = findRouteUseCase.findById(scheduleModel.getRouteId());
                Train train = findTrainUseCase.findById(scheduleModel.getTrainId());

                if(train.getCapacity() == 0 ){
                    throw new TrainNotAvailableException(String.format("Train %s not available because capacity is 0" , train.getTrainName()));
                }

                Schedule schedule = new Schedule();
                LocalDate startDate;
                if(scheduleModel.getStartTime() != null){
                    Timestamp startTime = toUTCVietNam(Timestamp.valueOf(scheduleModel.getStartTime()));
                    if(startTime.before(Timestamp.from(Instant.now()))) {
                        throw new InvalidStopSequence("Thời gian tạo không được trước thời gian hiện tại");
                    }
                    startDate = startTime.toInstant()
                            .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                            .toLocalDate();
                }else{
                    startDate = LocalDate.now().minusDays(1);
                }
                LocalDate tomorrow = startDate;

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

        private Timestamp toUTCVietNam(Timestamp timestamp){
            LocalDateTime localDateTime = timestamp.toLocalDateTime();

            // Chuyển sang UTC (từ múi giờ Việt Nam)
            ZonedDateTime vietnamZonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
            ZonedDateTime utcZonedDateTime = vietnamZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

            // Chuyển đổi UTC về Timestamp
            Timestamp utcTimestamp = Timestamp.valueOf(utcZonedDateTime.toLocalDateTime());
            return timestamp;
        }
}
