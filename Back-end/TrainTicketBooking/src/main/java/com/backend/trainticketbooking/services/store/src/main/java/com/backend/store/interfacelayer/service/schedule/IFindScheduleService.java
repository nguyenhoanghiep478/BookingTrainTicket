package com.backend.store.interfacelayer.service.schedule;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.interfacelayer.dto.objectDTO.ScheduleDTO;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface IFindScheduleService {
    Schedule findById(int id);


    List<ScheduleStation> findByDepartureTimesBetween(LocalDateTime now, LocalDateTime oneHourFromNow);

    List<NotificationRequest> findTicketBetweenDepartureTime(LocalDateTime now, LocalDateTime oneHourFromNow);

    List<Schedule> findRoundTrip(Integer departureStationId, Integer arrivalStationId,Integer scheduleId);

    List<Schedule> findByDepartAndArrival(Integer departureStationId, Integer arrivalStationId);

    List<Schedule> findByDepartAndArrivalName(String departureStation, String arrivalStation, Timestamp departureTime);
}
