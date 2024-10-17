package com.backend.store.application.usecase.Ticket;

import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.repository.ITicketRepository;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FindTicketUseCase {
    private final ITicketRepository ticketRepository;

    public List<Ticket> execute(List<Criteria> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return ticketRepository.findAll();
        }
        return ticketRepository.findBy(criteria);
    }
    public List<Ticket> findByScheduleId(Schedule schedule) {

        return ticketRepository.findByScheduleId(schedule);
    }

    public List<NotificationRequest> findTicketBetween(LocalDateTime now, LocalDateTime oneHourFromNow) {
        List<Object[]> results=  ticketRepository.findTicketBy1Hour();
        Map<String, NotificationRequest> notificationMap = new HashMap<>();

        for (Object[] result : results) {
            String email = (String) result[0];
            Timestamp departureTime = (Timestamp) result[1];
            String departureStation = (String) result[2];
            String customerName = (String) result[3];
            String trainName = (String) result[4];
            String seatNumber = (String) result[5];

            String key = email + departureTime.toString();

            if (notificationMap.containsKey(key)) {
                notificationMap.get(key).getSeatName().add(seatNumber);
            } else {
                NotificationRequest notification = new NotificationRequest();
                notification.setEmail(email);
                notification.setDepartureTime(departureTime);
                notification.setDepartureStation(departureStation);
                notification.setCustomerName(customerName);
                notification.setTrainName(trainName);
                notification.setSeatName(new ArrayList<>(List.of(seatNumber)));
                notificationMap.put(key, notification);
            }
        }

        return new ArrayList<>(notificationMap.values());
    }
}
