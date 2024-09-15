package com.booksms.payment.infrastructure.repository;

import com.booksms.payment.core.domain.entity.Payment;
import com.booksms.payment.core.domain.repository.IPaymentRepository;
import com.booksms.payment.infrastructure.jpaRepository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepository implements IPaymentRepository {
   private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Optional<Payment> save(Payment payment) {
        return Optional.of(paymentJpaRepository.save(payment));
    }
}
