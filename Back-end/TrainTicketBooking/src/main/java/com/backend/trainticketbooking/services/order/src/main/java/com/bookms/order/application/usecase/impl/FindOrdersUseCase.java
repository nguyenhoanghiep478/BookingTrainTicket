package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrdersSearchCriteria;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component
@RequiredArgsConstructor
public class FindOrdersUseCase implements BaseUseCase<List<Order>, OrdersSearchCriteria> {
    private final IOrderRepository orderRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Order> execute(OrdersSearchCriteria ordersSearchCriteria) {
        if(ordersSearchCriteria == null) {
            return orderRepository.findAll();
        }
        List<Order> result = null;
        if(ordersSearchCriteria.getCustomerId() != null && ordersSearchCriteria.getStatus() != null && ordersSearchCriteria.getTotalPrice() != null && ordersSearchCriteria.getPaymentMethod() != null) {
            result = orderRepository.findAllByCustomerIdAndOrderTypeAndStatusAndTotalPriceAndPaymentMethod(
                    ordersSearchCriteria.getCustomerId(),ordersSearchCriteria.getStatus(),ordersSearchCriteria.getTotalPrice(),ordersSearchCriteria.getPaymentMethod()
            );
        }
        else if(ordersSearchCriteria.getCustomerId() != null && ordersSearchCriteria.getStatus() != null && ordersSearchCriteria.getTotalPrice() != null) {
            result = orderRepository.findAllByCustomerIdAndOrderTypeAndStatus(
                    ordersSearchCriteria.getCustomerId(),ordersSearchCriteria.getStatus()
            );
        }
        else if(ordersSearchCriteria.getCustomerId() != null ) {
            result = orderRepository.findAllByCustomerIdAndOrderType(
                    ordersSearchCriteria.getCustomerId()
            );
        }
        else if(ordersSearchCriteria.getCustomerId() != null){
            result = orderRepository.findAllByCustomerId(
                    ordersSearchCriteria.getCustomerId()
            );
        }

        return result;

    }
}
