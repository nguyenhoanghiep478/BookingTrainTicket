package com.bookms.order.interfaceLayer.service;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.interfaceLayer.DTO.ChartDTO;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.ResponsePayment;
import com.bookms.order.interfaceLayer.DTO.TopSaleDTO;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> findAll();

    OrderDTO findById(int id);

    OrderDTO createOrder(OrdersModel request);

   OrderDTO findByOrderNumber(Long orderNumber);

    PaymentModel prePay(OrderDTO request);

    OrdersModel afterPay(Long orderNumber);

    void completeOrder(Long key);

    void handlePaymentResponse(OrderDTO response);

    List<OrderDTO> findLatest(int amount);

    OrdersModel handleOrderWasPaid(ResponsePayment responsePayment);

    List<TopSaleDTO> getTopSale();

    List<ChartDTO> getChartOrderInWeek();

    OrdersModel handleCodPaymentMethod(OrderDTO request);

    List<OrderDTO> findByCustomerId(int id);
}
