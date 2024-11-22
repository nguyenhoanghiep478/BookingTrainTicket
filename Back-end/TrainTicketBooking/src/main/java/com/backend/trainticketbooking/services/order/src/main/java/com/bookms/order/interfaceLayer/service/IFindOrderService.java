package com.bookms.order.interfaceLayer.service;

import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.interfaceLayer.DTO.ChartDTO;
import com.bookms.order.interfaceLayer.DTO.TopSaleDTO;

import java.util.List;

public interface IFindOrderService {
    Order findById(int id);
    Order findByOrderNumber(Long orderNumber);
    List<Order> findAll();

    List<Order> findLatest(int i);

    List<TopSaleDTO> getTopSales();

    List<ChartDTO> getChartOrderInWeek();

    List<Order> findByCustomerId(int id);
}
