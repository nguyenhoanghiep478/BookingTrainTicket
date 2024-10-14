package com.backend.store.interfacelayer.service.notification;

import static com.backend.store.core.domain.state.StaticVar.NOTIFICATIONS_DURATION_SECONDS;


import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.state.StaticVar;
import com.backend.store.interfacelayer.dto.request.NotificationRequest;
import com.backend.store.interfacelayer.service.schedule.IFindScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final IFindScheduleService findScheduleService;
    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    @Scheduled(fixedRate = 60000)
    public void checkAndSendDepartureNotifications(){
        log.info("Checking departure notifications");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourFromNow = LocalDateTime.now().plusHours(1);
        List<NotificationRequest> notifications = findScheduleService.findTicketBetweenDepartureTime(now,oneHourFromNow);
        for (NotificationRequest notification : notifications) {
            log.info(notification.toString());
            kafkaTemplate.send("notifications", notification);
        };

    }
}
