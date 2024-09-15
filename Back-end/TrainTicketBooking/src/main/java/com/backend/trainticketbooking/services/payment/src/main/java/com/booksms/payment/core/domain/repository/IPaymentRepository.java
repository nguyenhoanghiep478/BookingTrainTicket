package com.booksms.payment.core.domain.repository;

import com.booksms.payment.core.domain.entity.Payment;

import java.util.Optional;

public interface IPaymentRepository {
    Optional<Payment> save(Payment payment);
}
