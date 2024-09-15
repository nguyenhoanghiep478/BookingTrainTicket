package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.Criteria;
import com.bookms.order.application.model.OrderSearchCriteria;
import com.bookms.order.application.usecase.IFindOrderUseCase;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindOrderUseCase implements BaseUseCase<Orders, OrderSearchCriteria>{
    private  final IOrderRepository orderRepository;
    @Override
    @Transactional(readOnly = true)
    public Orders execute(OrderSearchCriteria orderSearchCriteria) {
        Orders orders = null;
        if(orderSearchCriteria != null){
            if(orderSearchCriteria.getId() != null && orderSearchCriteria.getOrderNumber() != null && orderSearchCriteria.getOrderType() != null && orderSearchCriteria.getCustomerId() != null){
                orders = orderRepository.findByIdAndOrderNumberAndCustomerIdAndOrderType(orderSearchCriteria.getId(),orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId(),orderSearchCriteria.getOrderType())
                        .orElseThrow(
                                () -> new OrderNotFoundException(String.format("Order with id:%s, order number:%s,customer id : %s , order type : %s not found",
                                        orderSearchCriteria.getId(),orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId(),orderSearchCriteria.getOrderType()))
                        );
            }
            else if(orderSearchCriteria.getOrderNumber() != null && orderSearchCriteria.getCustomerId() != null && orderSearchCriteria.getOrderType() !=null){
                orders = orderRepository.findByOrderNumberAndCustomerIdAndOrderType(orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId(),orderSearchCriteria.getOrderType())
                        .orElseThrow(()-> new OrderNotFoundException(String.format("Order with order number:%s,customer id : %s , order type : %s not found",
                                orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId(),orderSearchCriteria.getOrderType())));
            }
            else if(orderSearchCriteria.getOrderNumber() != null && orderSearchCriteria.getCustomerId() != null){
                orders = orderRepository.findByOrderNumberAndCustomerId(orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId())
                        .orElseThrow(() ->  new OrderNotFoundException(
                                String.format("Order with order number:%s,customer id : %s not found",orderSearchCriteria.getOrderNumber(),orderSearchCriteria.getCustomerId())
                            )
                        );
            }
            else if(orderSearchCriteria.getOrderNumber() != null){
                orders = orderRepository.findByOrderNumber(orderSearchCriteria.getOrderNumber()).orElseThrow(
                        () -> new OrderNotFoundException(
                                String.format("Order with order number:%s not found",orderSearchCriteria.getOrderNumber())
                        )
                );
            }
            else if(orderSearchCriteria.getId() != null){
                orders = orderRepository.findById(orderSearchCriteria.getId()).orElseThrow(
                        () -> new OrderNotFoundException(
                                String.format("Order with id:%s not found",orderSearchCriteria.getId())
                        )
                );
            }
            if(orderSearchCriteria.getStatus() != null && orders !=null && orders.getStatus() != null){
                return orders.getStatus() == orderSearchCriteria.getStatus() ? orders : null;
            }
            //someConditionElse
            return orders;
        }
        return null;
    }


}
