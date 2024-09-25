package com.backend.store.application.usecase.Ticket;

import com.backend.store.application.model.TicketModel;
import com.backend.store.application.usecase.Schedule.FindScheduleUseCase;
import com.backend.store.application.usecase.Seat.FindSeatUseCase;
import com.backend.store.application.usecase.Station.FindStationUseCase;
import com.backend.store.core.domain.entity.Booking.Ticket;
import com.backend.store.core.domain.entity.schedule.Schedule;
import com.backend.store.core.domain.entity.schedule.Station;
import com.backend.store.core.domain.entity.schedule.TicketSeat;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.exception.SeatInUseException;
import com.backend.store.core.domain.exception.StationNotExistException;
import com.backend.store.core.domain.repository.ITicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class CreateTicketUseCase {
    private final ITicketRepository ticketRepository;
    private final FindSeatUseCase findSeatUseCase;
    private final FindStationUseCase findStationUseCase;
    private final FindScheduleUseCase findScheduleUseCase;

    public Ticket execute(final TicketModel model){
        List<Seat> seat = findSeatUseCase.findInIdsAndAvailableSeatsAtStation(model.getSeatIds(),model.getScheduleId(),model.getDepartureStationId());
        List<Integer> stationIds = List.of(model.getDepartureStationId(), model.getArrivalStationId());
        List<Station> stations = findStationUseCase.getListStationInIds(stationIds);

        Ticket ticket = map(model,stations,seat);

        Ticket response = ticketRepository.save(ticket);

        for (TicketSeat orderedSeat : response.getTicketSeats()){
            orderedSeat.getSeat().setIsAvailable(false);
            orderedSeat.setTicket(ticket);
        }

        return response;
    }

    private Ticket map(final TicketModel model,List<Station> stations,List<Seat> orderedSeat){
        Schedule schedule = findScheduleUseCase.findById(model.getScheduleId());
        Ticket ticket = new Ticket();
        ticket.setDepartureStation(stations.get(0));
        ticket.setArrivalStation(stations.get(1));
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<TicketSeat> seats = ticket.getTicketSeats();
        for (Seat seat : orderedSeat){
            TicketSeat ticketSeat = new TicketSeat();
            ticketSeat.setSeat(seat);
            ticketSeat.setTicket(ticket);
            ticketSeat.setPrice(model.getPrice());
            seats.add(ticketSeat);
            seat.getTicket().add(ticketSeat);
            totalPrice = totalPrice.add(model.getPrice());
        }
        ticket.setTotalPrice(totalPrice);
        ticket.setTicketSeats(seats);
        ticket.setSchedule(schedule);
        return ticket;
    }
}
