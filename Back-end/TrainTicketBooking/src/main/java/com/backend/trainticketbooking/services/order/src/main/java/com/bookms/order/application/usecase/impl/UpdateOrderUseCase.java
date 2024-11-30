package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateOrderUseCase implements BaseUseCase<Order, OrdersModel> {
    private final IOrderRepository repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order execute(OrdersModel ordersModel) {


        Order order = repository.findByOrderNumber(ordersModel.getOrderNumber()).orElseThrow(
                () -> new OrderNotFoundException(String.format("Order %s not found", ordersModel.getOrderNumber()))
        );

        order.setPaymentId(ordersModel.getPaymentId());
        order.setPaymentMethod(ordersModel.getPaymentMethod().getValue());
        order.setStatus(ordersModel.getStatus());

        if(ordersModel.getIsHaveRoundTrip()){
            Order roundTripOrder = repository.findByOrderNumber(ordersModel.getOrderNumber()+1).orElseThrow(
                    () -> new OrderNotFoundException(String.format("Order %s not found", ordersModel.getOrderNumber()))
            );

            roundTripOrder.setPaymentId(ordersModel.getPaymentId());
            roundTripOrder.setPaymentMethod(ordersModel.getPaymentMethod().getValue());
            roundTripOrder.setStatus(ordersModel.getStatus());

            repository.save(roundTripOrder);
        }
        return repository.save(order);
    }
}
