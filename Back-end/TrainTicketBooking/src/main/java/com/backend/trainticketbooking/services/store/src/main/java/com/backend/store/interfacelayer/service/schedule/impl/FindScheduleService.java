package com.backend.store.interfacelayer.service.schedule.impl;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.usecase.Schedule.FindScheduleUseCase;
import com.backend.store.application.usecase.Ticket.FindTicketUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import com.backend.store.interfacelayer.service.schedule.IFindScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FindScheduleService implements IFindScheduleService {
    private final FindScheduleUseCase findScheduleUseCase;
    private final FindTicketUseCase findTicketUseCase;

    @Override
    public Schedule findById(int id) {
        return findScheduleUseCase.findById(id);
    }

    @Override
    public List<ScheduleStation> findByDepartureTimesBetween(LocalDateTime now, LocalDateTime oneHourFromNow) {
        List<Schedule> schedules = findScheduleUseCase.execute(null);
        List<ScheduleStation> scheduleStations = new ArrayList<>();
        schedules.stream().map(schedule ->
                schedule.getScheduleStations().stream().filter(
                        scheduleStation -> {
                            Timestamp departureTime = scheduleStation.getDepartureTime();
                            if(departureTime.after(Timestamp.valueOf(now)) && departureTime.before(Timestamp.valueOf(oneHourFromNow))) {
                                scheduleStations.add(scheduleStation);
                            }
                            return false;
                        }
                ).toList()
        );
        return scheduleStations;
    }

    @Override
    public List<NotificationRequest> findTicketBetweenDepartureTime(LocalDateTime now, LocalDateTime oneHourFromNow) {
        List<Schedule> schedules = findScheduleUseCase.execute(null);
        List<ScheduleStation> scheduleStations = findByDepartureTimesBetween(now, oneHourFromNow);
        Map<Integer,ScheduleStation> scheduleStationMap = new HashMap<>();
        List<NotificationRequest> notificationRequests = new ArrayList<>();
        for(ScheduleStation scheduleStation : scheduleStations) {
            scheduleStationMap.put(scheduleStation.getId(), scheduleStation);
        }
        for(Schedule schedule : schedules) {
           List<Ticket> currentTickets =  findTicketUseCase.findByScheduleId(schedule);
           for(Ticket ticket : currentTickets) {
               if(ticket.getDepartureStation() == null){
                   continue;
               }
               if(scheduleStations.contains(ticket.getDepartureStation())){
                   ScheduleStation scheduleStation = scheduleStationMap.get(ticket.getDepartureStation().getId());
                  NotificationRequest notificationRequest = NotificationRequest.builder()
                          .departureTime(scheduleStation.getDepartureTime())
                          .departureStation(ticket.getDepartureStation().getName())
                          .trainName(scheduleStation.getSchedule().getTrain().getTrainName())
                          .customerName(ticket.getCustomerName())
                          .email(ticket.getEmail())
                          .seatName(ticket.getTicketSeats()
                                  .stream()
                                  .map(seat -> seat.getSeat().getSeatNumber())
                                  .toList()
                          )
                          .build();
                  notificationRequests.add(notificationRequest);
               }
           }
        }
        return notificationRequests;
    }

    @Override
    public List<Schedule> findRoundTrip(Integer departureStationId, Integer arrivalStationId,Integer scheduleId) {
       List<Schedule> schedules = findScheduleUseCase.findRoundTrip(departureStationId,arrivalStationId,scheduleId);

        return schedules.isEmpty() ? null : schedules;
    }

    @Override
    public List<Schedule> findByDepartAndArrival(Integer departureStationId, Integer arrivalStationId) {
        List<Schedule> schedules = findScheduleUseCase.findByDepartAndArrival(departureStationId,arrivalStationId);
        return schedules == null ? null : schedules;
    }

    @Override
    public List<Schedule> findByDepartAndArrivalName(String departureStation, String arrivalStation,Timestamp departureTime) {
        List<Schedule> schedules = findScheduleUseCase.findByDepartAndArrivalName(departureStation,arrivalStation,departureTime);
        return schedules == null ? null : schedules;
    }


}
