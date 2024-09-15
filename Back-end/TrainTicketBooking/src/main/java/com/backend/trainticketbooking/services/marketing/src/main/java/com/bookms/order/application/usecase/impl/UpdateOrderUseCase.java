package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateOrderUseCase implements BaseUseCase<Orders, OrdersModel> {
    private final IOrderRepository repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Orders execute(OrdersModel ordersModel) {
        Orders orders = repository.findByOrderNumber(ordersModel.getOrderNumber()).orElseThrow(
                () -> new OrderNotFoundException(String.format("Order %s not found", ordersModel.getOrderNumber()))
        );

        orders.setPaymentId(ordersModel.getPaymentId());
        orders.setPaymentMethod(ordersModel.getPaymentMethod());
        orders.setShipmentId(ordersModel.getShipmentId());
        orders.setStatus(ordersModel.getStatus());

        return repository.save(orders);
    }
}
