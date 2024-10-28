package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateBuyOrderUseCase implements BaseUseCase<Order, Order> {
    private final IOrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public Order execute(Order order) {
        return orderRepository.save(order);
    }
}
