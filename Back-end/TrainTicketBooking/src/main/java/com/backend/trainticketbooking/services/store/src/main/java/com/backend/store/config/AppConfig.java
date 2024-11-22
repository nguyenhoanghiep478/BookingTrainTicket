package com.backend.store.config;

import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.entity.train.Train;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Class<Railcar> railcarClass(){
        return Railcar.class;
    }

    @Bean
    public Class<Train> trainClass(){
        return Train.class;
    }

    @Bean
    public Class<Seat> seatClass(){
        return Seat.class;
    }

    @Bean
    public Class<Station> stationClass(){
        return Station.class;
    }

    @Bean
    public Class<Route> routeClass(){
        return Route.class;
    }

    @Bean
    public Class<Schedule> scheduleClass(){
        return Schedule.class;
    }

    @Bean
    public Class<Ticket> ticketClass(){
        return Ticket.class;
    }

    @Bean
    public Class<ScheduleStation> scheduleStationClass(){
        return ScheduleStation.class;
    }
}
