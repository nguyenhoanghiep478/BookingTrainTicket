package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateBuyOrderUseCase implements BaseUseCase<Orders,Orders> {
    private final IOrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public Orders execute(Orders orders) {
        return orderRepository.save(orders);
    }
}
