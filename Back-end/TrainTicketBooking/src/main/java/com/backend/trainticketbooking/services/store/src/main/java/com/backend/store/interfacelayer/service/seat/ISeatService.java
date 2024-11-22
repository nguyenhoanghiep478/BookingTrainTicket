package com.backend.store.interfacelayer.service.seat;

import com.backend.store.interfacelayer.dto.objectDTO.SeatDTO;
import com.backend.store.interfacelayer.dto.objectDTO.TicketDTO;

import java.util.List;

public interface ISeatService {
    List<SeatDTO> checkSeat(List<Integer> ids,Integer scheduleId,Integer departureStationId);
}
