package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.usecase.impl.UpdateOrderUseCase;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.infrastructure.jpaRepository.OrderJpaRepository;
import com.bookms.order.interfaceLayer.service.IUpdateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderService implements IUpdateOrderService {
    private final UpdateOrderUseCase updateOrderUseCase;
    private final OrderJpaRepository orderJpaRepository;


}
