package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrderSearchCriteria;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindOrderUseCase implements BaseUseCase<Order, OrderSearchCriteria>{
    private  final IOrderRepository orderRepository;
    @Override
    @Transactional(readOnly = true)
    public Order execute(OrderSearchCriteria orderSearchCriteria) {
        Order order = null;
        if(orderSearchCriteria != null){
            if(orderSearchCriteria.getId() != null && orderSearchCriteria.getOrderNumber() != null  && orderSearchCriteria.getCustomerId() != null){
                order = orderRepository.findByIdAndOrderNumberAndCustomerIdAndOrderType(orderSearchCriteria.getId(),orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId())
                        .orElseThrow(
                                () -> new OrderNotFoundException(String.format("Order with id:%s, order number:%s,customer id : %s , order type : %s not found",
                                        orderSearchCriteria.getId(),orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId()))
                        );
            }
            else if(orderSearchCriteria.getOrderNumber() != null && orderSearchCriteria.getCustomerId() != null){
                order = orderRepository.findByOrderNumberAndCustomerIdAndOrderType(orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId())
                        .orElseThrow(()-> new OrderNotFoundException(String.format("Order with order number:%s,customer id : %s ",
                                orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId())));
            }
            else if(orderSearchCriteria.getOrderNumber() != null){
                order = orderRepository.findByOrderNumber(orderSearchCriteria.getOrderNumber()).orElseThrow(
                        () -> new OrderNotFoundException(
                                String.format("Order with order number:%s not found",orderSearchCriteria.getOrderNumber())
                        )
                );
            }
            else if(orderSearchCriteria.getId() != null){
                order = orderRepository.findById(orderSearchCriteria.getId()).orElseThrow(
                        () -> new OrderNotFoundException(
                                String.format("Order with id:%s not found",orderSearchCriteria.getId())
                        )
                );
            }
            if(orderSearchCriteria.getStatus() != null && order !=null && order.getStatus() != null){
                return order.getStatus() == orderSearchCriteria.getStatus() ? order : null;
            }
            //someConditionElse
            return order;
        }
        return null;
    }


}
