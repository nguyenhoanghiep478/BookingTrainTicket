package com.backend.store.application.usecase.Seat;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;
import com.backend.store.application.model.Criteria;
import com.backend.store.application.usecase.Railcar.FindRailcarUseCase;
import com.backend.store.core.domain.entity.train.Railcar;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.exception.SeatInUseException;
import com.backend.store.core.domain.exception.SeatNotExistException;
import com.backend.store.core.domain.repository.ISeatRepository;
import com.backend.store.interfacelayer.dto.objectDTO.RailcarDTO;
import com.backend.store.interfacelayer.dto.objectDTO.ShortSeatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FindSeatUseCase {
    private final ISeatRepository seatRepository;
    private final FindRailcarUseCase findRailcarUseCase;

    public List<Seat> execute(List<Criteria> criteria){
        if(criteria == null || criteria.isEmpty()){
            return seatRepository.findAll();
        }
        return seatRepository.findBy(criteria);
    }

    public Seat findById(int id){
        return seatRepository.findById(id).orElseThrow(
                ()  -> new SeatNotExistException("Seat with id " + id + " does not exist")
        );
    }

    public List<Seat> findInIds(List<Integer> ids){
        Criteria criteria = Criteria.builder()
                .key("id")
                .operation("IN")
                .value(ids)
                .build();

        return execute(List.of(criteria));
    }

    public List<Seat> findInIdsAndAvailableSeatsAtStation(List<Integer> ids,Integer scheduleId,Integer departureStationId,Integer arrivalStationId){


        List<Integer> idsAvailable = seatRepository.checkSeatsAtStation(ids,scheduleId,departureStationId,arrivalStationId);


        if(idsAvailable.isEmpty() || idsAvailable.size() != ids.size()){
            List<Integer> idsNotAvailable = ids.stream().filter(id -> !idsAvailable.contains(id)).toList();
            throw new SeatInUseException("The following seat IDs not available: " + idsNotAvailable);
        }
        return findInIds(idsAvailable);
    }

    public List<AvailableSeatNumberForSchedule> findTotalAvailableSeatsAtSchedule(Integer scheduleId){
        List<Object[]> objects =  seatRepository.findTotalAvailableSeatAtStation(scheduleId);
        List<AvailableSeatNumberForSchedule> availableSeatNumberForSchedules = new ArrayList<>();
        for(Object[] object: objects){
            Integer rowScheduleId = (Integer) object[0];
            String railcarName = (String) object[1];
            String rowSeatIds = (String) object[2];
            String rowSeatNumbers = (String) object[3];
            List<String> seatIds = List.of(rowSeatIds.split(","));
            List<String> seatNumbers = List.of(rowSeatNumbers.split(","));
            availableSeatNumberForSchedules.add(AvailableSeatNumberForSchedule.builder()
                            .scheduleId(rowScheduleId)
                            .seatNumbers(seatNumbers)
                            .seatIds(seatIds)
                            .railcarName(railcarName)
                    .build());

        }
        return availableSeatNumberForSchedules;
    }

    public List<RailcarDTO> findSeatAvailableGroupByRailcarAt(Integer departureStationId, Integer arrivalStationId, Integer scheduleId) {
        List<Object[]> rawData = seatRepository.findAvailableSeatGroupByRailcarAt(departureStationId,arrivalStationId,scheduleId);
        List<RailcarDTO> railcarDTOs = new ArrayList<>();
        boolean isAvailable;
        for(Object[] object: rawData){
            List<ShortSeatDTO> seats = new ArrayList<>();
           String railcarName = (String) object[0];
           String rowSeatNumbers = (String) object[2];
           List<String> seatNumbers = List.of(rowSeatNumbers.split(","));
           Railcar railcar = findRailcarUseCase.findByRailcarName(railcarName);
           for(Seat seat : railcar.getSeats()){
               isAvailable = seatNumbers.contains(seat.getSeatNumber());
                seats.add(toShortSeatDTO(seat,isAvailable));
           }

            RailcarDTO railcarDTO = RailcarDTO.builder()
                    .railcarName(railcarName)
                    .totalSeatAvailable(seatNumbers.size())
                    .totalSeat(railcar.getSeats().size())
                    .seats(seats)
                    .build();
           railcarDTOs.add(railcarDTO);
        }
        return railcarDTOs;
    }

    private ShortSeatDTO toShortSeatDTO(Seat seat,Boolean isAvailable){
        return ShortSeatDTO.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .isAvailable(isAvailable)
                .build();
    }
}
