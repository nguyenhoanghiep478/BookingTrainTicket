package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.application.servicegateway.IBookServiceGateway;
import com.bookms.order.application.usecase.IFindOrderUseCase;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class FindOrderUseCaseWithCriteria implements IFindOrderUseCase {
    private final IOrderRepository repository;
    private final IBookServiceGateway bookServiceGateway;

    @Override
    public List<Order> execute(List<Criteria> criteria) {
        return repository.findByCriteria(criteria);
    }

}
