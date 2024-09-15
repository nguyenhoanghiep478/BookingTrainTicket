package com.bookms.order.core.domain.Repository;

import com.bookms.order.core.domain.Entity.OrderItems;

import java.util.List;

public interface IOrderItemRepository {
    List<Integer> findTopSales();
}
