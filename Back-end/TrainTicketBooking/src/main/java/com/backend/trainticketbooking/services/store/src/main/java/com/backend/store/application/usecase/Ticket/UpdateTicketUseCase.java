package com.backend.store.application.usecase.Ticket;

import com.backend.store.application.model.Criteria;
import com.backend.store.application.model.TicketModel;
import com.backend.store.application.usecase.Schedule.FindScheduleUseCase;
import com.backend.store.application.usecase.Seat.FindSeatUseCase;
import com.backend.store.application.usecase.Seat.UpdateSeatUseCase;
import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.schedule.TicketSeat;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.exception.InvalidStopSequence;
import com.backend.store.core.domain.repository.ITicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class UpdateTicketUseCase {
    private final ITicketRepository ticketRepository;
    private final FindTicketUseCase findTicketUseCase;
    private final FindScheduleUseCase findScheduleUseCase;
    private final FindStationUseCase findStationUseCase;
    private final FindSeatUseCase findSeatUseCase;
    private final UpdateSeatUseCase updateSeatUseCase;
    public boolean execute(TicketModel ticketModel){
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation(":")
                .value(ticketModel.getId())
                .build();
        Ticket ticket = findTicketUseCase.execute(List.of(criteria)).get(0);
        ticket = map(ticket,ticketModel);

        return ticketRepository.save(ticket) != null;
    }

    private Ticket map(Ticket ticket, TicketModel ticketModel) {
        Station departureStation = ticket.getDepartureStation();
        Station arrivalStation = ticket.getArrivalStation();
        Schedule schedule = ticket.getSchedule();
        if (ticketModel.getPrice() != null){
            ticket.setTotalPrice(ticketModel.getPrice());
        }

        if (ticketModel.getStatus() != null)
            ticket.setStatus(ticketModel.getStatus());

        if(ticketModel.getScheduleId() != null){
            schedule = findScheduleUseCase.findById(ticketModel.getId());
            ticket.setSchedule(schedule);
        }
        //update both departure station and arrival station
        if(ticketModel.getDepartureStationId() != null && ticketModel.getArrivalStationId() != null){
            departureStation = findStationUseCase.getStationById(ticketModel.getDepartureStationId());
            arrivalStation = findStationUseCase.getStationById(ticketModel.getArrivalStationId());
        }


        if(ticketModel.getDepartureStationId() != null){
            departureStation = findStationUseCase.getStationById(ticketModel.getDepartureStationId());

        }

        if (ticketModel.getArrivalStationId() != null){
            arrivalStation = findStationUseCase.getStationById(ticketModel.getArrivalStationId());
        }

        isValidUpdateData(departureStation,arrivalStation,schedule);

        if(ticketModel.getSeatIds() != null){
            List<Seat> seats = findSeatUseCase
                    .findInIdsAndAvailableSeatsAtStation(
                            ticketModel.getSeatIds()
                            ,schedule.getId()
                            ,departureStation.getId()
                            , arrivalStation.getId()
                    );
            AtomicInteger index = new AtomicInteger(0);
            List<TicketSeat> ticketSeats = ticket.getTicketSeats().stream().map(ticketSeat -> {
                updateSeatUseCase.changeStatus(ticketSeat.getSeat().getId(),true);
                ticketSeat.setSeat(seats.get(index.getAndIncrement()));
                return ticketSeat;
            }).toList();
            ticket.setTicketSeats(ticketSeats);
        }

        ticket.setDepartureStation(departureStation);
        ticket.setArrivalStation(arrivalStation);

        return ticket;
    }

    private boolean isValidUpdateData(Station departureStation,Station arrivalStation,Schedule schedule){
        int departureStopSequence = departureStation.getSchedules().stream()
                .filter(scheduleStation -> scheduleStation.getSchedule().equals(schedule))
                .findFirst()
                .get().getStopSequence();
        int arrivalStopSequence = arrivalStation.getSchedules().stream()
                .filter(scheduleStation -> scheduleStation.getSchedule().equals(schedule))
                .findFirst()
                .get().getStopSequence();
        if(departureStopSequence >= arrivalStopSequence) {
            throw new InvalidStopSequence(String.format("Station %s is not arrival of station %s",arrivalStation.getName(),departureStation.getName()));
        }

        return true;
    }
}
