package com.bookms.order.core.domain.Repository;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Entity.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
    Optional<Order> findByOrderNumberAndCustomerIdAndOrderType(Long orderNumber, Integer customerId);

    Optional<Order> findByOrderNumberAndCustomerId(Long orderNumber, Integer customerId);

    Optional<Order> findByOrderNumber(Long orderNumber);

    Optional<Order> findByIdAndOrderNumberAndCustomerIdAndOrderType(Integer id, Long orderNumber, Integer customerId);

    Optional<Order> findById(Integer id);

    List<Order> findAllByCustomerIdAndOrderTypeAndStatusAndTotalPriceAndPaymentMethod(Integer customerId, Status status, BigDecimal totalPrice, String paymentMethod);

    List<Order> findAllByCustomerIdAndOrderTypeAndStatus(Integer customerId, Status status);

    List<Order> findAllByCustomerIdAndOrderType(Integer customerId);

    List<Order> findAllByCustomerId(Integer customerId);


    List<Order> findAll();

    Order save(Order order);

    List<Order> findByCriteria(List<Criteria> criteria);

    Object findByNativeQuery(String sql);
}
