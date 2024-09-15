package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrdersSearchCriteria;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component
@RequiredArgsConstructor
public class FindOrdersUseCase implements BaseUseCase<List<Orders>, OrdersSearchCriteria> {
    private final IOrderRepository orderRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Orders> execute(OrdersSearchCriteria ordersSearchCriteria) {
        if(ordersSearchCriteria == null) {
            return orderRepository.findAll();
        }
        List<Orders> result = null;
        if(ordersSearchCriteria.getCustomerId() != null && ordersSearchCriteria.getOrderType() != null && ordersSearchCriteria.getStatus() != null && ordersSearchCriteria.getTotalPrice() != null && ordersSearchCriteria.getPaymentMethod() != null) {
            result = orderRepository.findAllByCustomerIdAndOrderTypeAndStatusAndTotalPriceAndPaymentMethod(
                    ordersSearchCriteria.getCustomerId(),ordersSearchCriteria.getOrderType(),ordersSearchCriteria.getStatus(),ordersSearchCriteria.getTotalPrice(),ordersSearchCriteria.getPaymentMethod()
            );
        }
        else if(ordersSearchCriteria.getCustomerId() != null && ordersSearchCriteria.getOrderType() != null && ordersSearchCriteria.getStatus() != null && ordersSearchCriteria.getTotalPrice() != null) {
            result = orderRepository.findAllByCustomerIdAndOrderTypeAndStatus(
                    ordersSearchCriteria.getCustomerId(),ordersSearchCriteria.getOrderType(),ordersSearchCriteria.getStatus()
            );
        }
        else if(ordersSearchCriteria.getCustomerId() != null && ordersSearchCriteria.getOrderType() != null) {
            result = orderRepository.findAllByCustomerIdAndOrderType(
                    ordersSearchCriteria.getCustomerId(),ordersSearchCriteria.getOrderType()
            );
        }
        else if(ordersSearchCriteria.getCustomerId() != null){
            result = orderRepository.findAllByCustomerId(
                    ordersSearchCriteria.getCustomerId()
            );
        }
        else if(ordersSearchCriteria.getOrderType() != null){
            result = orderRepository.findAllByOrderType(
                    ordersSearchCriteria.getOrderType()
            );
        }
        return result;

    }
}
