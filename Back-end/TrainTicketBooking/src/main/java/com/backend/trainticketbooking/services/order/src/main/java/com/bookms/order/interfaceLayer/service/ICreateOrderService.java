package com.bookms.order.interfaceLayer.service;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.ResponsePayment;

public interface ICreateOrderService {
    OrdersModel createOrder(OrdersModel request);

    PaymentModel prePayment(OrderDTO request);

    OrdersModel afterPayment(Long orderNumber);

    void completeOrder(Long key);

    OrdersModel handleOrderWasPaid(ResponsePayment responsePayment);

    OrdersModel handleCodOrder(OrdersModel model);
}
