package com.bookms.order.application.usecase;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.core.domain.Entity.Orders;

import java.util.List;

public interface IFindOrderUseCase {
    List<Orders> execute(List<Criteria> build);
}
