package com.bookms.order.interfaceLayer.service;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.interfaceLayer.DTO.ChartDTO;
import com.bookms.order.interfaceLayer.DTO.TopSaleDTO;

import java.util.List;

public interface IFindOrderService {
    Orders findById(int id);
    Orders findByOrderNumber(Long orderNumber);
    List<Orders> findAll();

    List<Orders> findLatest(int i);

    List<TopSaleDTO> getTopSales();

    List<ChartDTO> getChartOrderInWeek();

    List<Orders> findByCustomerId(int id);
}
