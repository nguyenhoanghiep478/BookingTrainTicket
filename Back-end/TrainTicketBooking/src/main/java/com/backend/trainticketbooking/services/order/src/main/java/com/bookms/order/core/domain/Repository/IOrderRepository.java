package com.bookms.order.core.domain.Repository;

import com.bookms.order.application.model.Criteria;
import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Entity.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
    Optional<Orders> findByOrderNumberAndCustomerIdAndOrderType(Long orderNumber, Integer customerId, OrderType orderType);

    Optional<Orders> findByOrderNumberAndCustomerId(Long orderNumber, Integer customerId);

    Optional<Orders> findByOrderNumber(Long orderNumber);

    Optional<Orders> findByIdAndOrderNumberAndCustomerIdAndOrderType(Integer id, Long orderNumber, Integer customerId, OrderType orderType);

    Optional<Orders> findById(Integer id);

    List<Orders> findAllByCustomerIdAndOrderTypeAndStatusAndTotalPriceAndPaymentMethod(Integer customerId, OrderType orderType, Status status, BigDecimal totalPrice, String paymentMethod);

    List<Orders> findAllByCustomerIdAndOrderTypeAndStatus(Integer customerId, OrderType orderType, Status status);

    List<Orders> findAllByCustomerIdAndOrderType(Integer customerId, OrderType orderType);

    List<Orders> findAllByCustomerId(Integer customerId);

    List<Orders> findAllByOrderType(OrderType orderType);

    List<Orders> findAll();

    Orders save(Orders orders);

    List<Orders> findByCriteria(List<Criteria> criteria);

    Object findByNativeQuery(String sql);
}
