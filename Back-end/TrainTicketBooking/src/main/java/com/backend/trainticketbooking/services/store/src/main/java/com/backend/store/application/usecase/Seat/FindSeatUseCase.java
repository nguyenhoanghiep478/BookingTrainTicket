package com.backend.store.application.usecase.Seat;

import com.backend.store.application.model.AvailableSeatNumberForSchedule;
import com.backend.store.application.model.Criteria;
import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.exception.SeatInUseException;
import com.backend.store.core.domain.exception.SeatNotExistException;
import com.backend.store.core.domain.repository.ISeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindSeatUseCase {
    private final ISeatRepository seatRepository;

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

    public List<Seat> findInIdsAndAvailableSeatsAtStation(List<Integer> ids,Integer scheduleId,Integer departureStationId){
        List<Integer> idsAvailable = seatRepository.checkSeatsAtStation(ids,scheduleId,departureStationId);
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
            String rowSeatNumbers = (String) object[1];
            List<String> seatNumbers = List.of(rowSeatNumbers.split(","));
            availableSeatNumberForSchedules.add(AvailableSeatNumberForSchedule.builder()
                            .scheduleId(rowScheduleId)
                            .seatNumbers(seatNumbers)
                    .build());

        }
        return availableSeatNumberForSchedules;
    }
}
