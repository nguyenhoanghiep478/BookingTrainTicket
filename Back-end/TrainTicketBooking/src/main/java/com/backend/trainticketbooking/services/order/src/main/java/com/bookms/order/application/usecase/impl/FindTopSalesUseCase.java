package com.bookms.order.application.usecase.impl;

import com.bookms.order.core.domain.Repository.IOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindTopSalesUseCase {
    private final IOrderItemRepository repository;

    public List<Integer> execute(){
        return repository.findTopSales();
    }

}
