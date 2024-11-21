package com.bookms.order.application.usecase.impl;

import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Exception.CreateFailedException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class CreateSellOrderUseCase {
    private final IOrderRepository orderRepository;
    public Order execute(Order order){
        Order result =  orderRepository.save(order);
        if(result == null){
            throw new CreateFailedException("create order failed");
        }
        return result;
    }

}
