package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindSpecialFieldUseCase {
    private final IOrderRepository repository;

    public Object execute(List<Criteria> condition){
        return repository.findByNativeQuery(condition.get(0).getSql());
    }

}
