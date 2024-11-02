package com.backend.store.interfacelayer.service.seat.impl;

import com.backend.store.core.domain.entity.train.Seat;
import com.backend.store.interfacelayer.dto.objectDTO.SeatDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;
import com.backend.store.interfacelayer.service.seat.IFindSeatService;
import com.backend.store.interfacelayer.service.seat.ISeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final IFindSeatService findSeatService;
    private final ModelMapper modelMapper;

    @Override
    public List<SeatDTO> checkSeat(List<Integer> ids,Integer scheduleId,Integer departureStationId,Integer arrivalStationId) {
        List<Seat> seats = findSeatService.findAvailableSeatInIds(ids,scheduleId,departureStationId,arrivalStationId);
        return seats.stream().map(seat -> modelMapper.map(seat, SeatDTO.class)).toList();
    }

}
