package com.bookms.order.infrastructure.jpaRepository;

import com.bookms.order.core.domain.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findByOrderNumber(Long orderNumber);
    Optional<Orders> findOneByShipmentId(Integer shipmentId);
}
