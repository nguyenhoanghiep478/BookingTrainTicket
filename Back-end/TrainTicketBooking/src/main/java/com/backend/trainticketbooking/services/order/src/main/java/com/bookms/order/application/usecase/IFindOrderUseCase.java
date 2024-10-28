package com.bookms.order.application.usecase;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.core.domain.Entity.Order;

import java.util.List;

public interface IFindOrderUseCase {
    List<Order> execute(List<Criteria> build);
}
