package com.booksms.payment.infrastructure.jpaRepository;

import com.booksms.payment.core.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Integer> {
}
