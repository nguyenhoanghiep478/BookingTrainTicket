package com.backend.store.interfacelayer.service.seat;

import com.backend.store.core.domain.entity.train.Seat;

import java.util.List;

public interface IUpdateSeatService {
  void holdSeat(List<Seat> seats);
}
