package com.backend.store.interfacelayer.service.seat.impl;

import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.core.domain.exception.SeatInUseException;
import com.backend.store.interfacelayer.dto.objectDTO.SeatDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.service.seat.IFindSeatService;
import com.backend.store.interfacelayer.service.seat.ISeatService;
import com.backend.store.interfacelayer.service.seat.IUpdateSeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final IFindSeatService findSeatService;
    private final IUpdateSeatService updateSeatService;
    private final ModelMapper modelMapper;

    @Override
    public List<SeatDTO> checkSeat(List<Integer> ids,Integer scheduleId,Integer departureStationId,Integer arrivalStationId) {
        List<Seat> seats = findSeatService.findAvailableSeatInIds(ids,scheduleId,departureStationId,arrivalStationId);
        for(Seat seat : seats) {
            if(!seat.getIsAvailable()){
               throw new SeatInUseException("seat in another transaction,please try again");
            }
        }
        holdSeat(seats);
        return seats.stream().map(seat -> modelMapper.map(seat, SeatDTO.class)).toList();
    }

    private void holdSeat(List<Seat> seats){
        updateSeatService.holdSeat(seats);
    }



}
