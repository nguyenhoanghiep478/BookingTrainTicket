package com.bookms.order.infrastructure.jpaRepository;

import com.bookms.order.core.domain.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderNumber(Long orderNumber);
}
