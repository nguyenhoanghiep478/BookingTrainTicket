package com.backend.store.application.usecase.monitoring;

import com.backend.store.application.model.MonitoringModel;
import com.backend.store.application.usecase.Schedule.FindScheduleUseCase;
import com.backend.store.application.usecase.Schedule.UpdateScheduleUseCase;
import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.application.usecase.Train.FindTrainUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.state.TicketStatus;
import com.backend.store.core.domain.entity.schedule.Route;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.ScheduleStation;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.train.Train;
import com.backend.store.core.domain.exception.*;
import com.backend.store.interfacelayer.dto.response.MonitoringResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateCurrentScheduleUseCase {
    private final FindTrainUseCase findTrainUseCase;
    private final FindScheduleUseCase findScheduleUseCase;
    private final FindStationUseCase findStationUseCase;
    private final UpdateScheduleUseCase updateScheduleUseCase;
    public MonitoringResponse execute(final MonitoringModel monitoringModel) {
        Train train = findTrainUseCase.findById(monitoringModel.getTrainId());
        if(train == null) {
            throw new TrainNotExistedException(String.format("Train with id %s not found", monitoringModel.getTrainId()));
        }
        Schedule schedule = findScheduleUseCase.findById(monitoringModel.getScheduleId());
        if(schedule == null) {
            throw new ScheduleNotExistException(String.format("Schedule with id %s not found", monitoringModel.getScheduleId()));
        }
        Station station = findStationUseCase.getStationById(monitoringModel.getCurrentStationId());
        if (station == null) {
            throw new StationNotExistException(String.format("Station with id %s not found", monitoringModel.getCurrentStationId()));
        }

        validate(schedule, station,train);

        train.setCurrentStation(station);
        //update time by actual time
        updateScheduleUseCase.updateByActualDepartureTime(schedule,station);

        Integer totalTicketCompleted = updateSeat(schedule,train,station);
        return MonitoringResponse.builder()
                .trainName(train.getTrainName())
                .currentStation(station.getName())
                .totalTicketCompleted(totalTicketCompleted)
                .build();

    }

    private void validate(final Schedule schedule,final Station station ,final Train train){
        ScheduleStation  scheduleStation= schedule.getScheduleStations().stream().filter(finding -> finding.getStation().equals(station)).findFirst().orElseThrow(
                () -> new StationNotInSchedule(String.format("Station with id %s not in schedule", station.getId()))
        );

        Route route = schedule.getRoute();
        Integer updateStationStopOrder = route.getRouteStations().stream()
                .filter(finding -> finding.getStation().equals(station))
                .findFirst()
                .get().getStopOrder();
        Integer currentStationStopOrder = route.getRouteStations().stream()
                .filter(finding -> finding.getStation().equals(train.getCurrentStation()))
                .findFirst()
                .get().getStopOrder();
        if(currentStationStopOrder+1 != updateStationStopOrder){
            throw new InvalidStopSequenceException(String.format("Station %s not the departure station of station %s",station.getName(),train.getCurrentStation().getName()));
        }
    }

    private int updateSeat(final Schedule schedule,Train train,Station currentStation){
        List<Ticket> tickets = schedule.getTickets();
        int totalTicketCompleted = 0;
        for(Ticket ticket : tickets){
            //completed ticket and change seat status
            if(ticket.getDepartureStation().equals(currentStation)){
                totalTicketCompleted++;
                ticket.setStatus(TicketStatus.COMPLETED);
                ticket.getTicketSeats().stream().map(
                        ticketSeat ->{
                            ticketSeat.getSeat().setIsAvailable(true);
                            return ticketSeat;
                        }
                );
            }
        }
        return totalTicketCompleted;
    }


}
