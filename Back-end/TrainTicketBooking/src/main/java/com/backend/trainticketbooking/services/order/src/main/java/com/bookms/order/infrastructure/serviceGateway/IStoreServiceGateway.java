package com.bookms.order.infrastructure.serviceGateway;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.SeatModel;

import java.util.List;

public interface IStoreServiceGateway {
    List<SeatModel> getSeatsAvailableInSystem(OrdersModel orders);
}
