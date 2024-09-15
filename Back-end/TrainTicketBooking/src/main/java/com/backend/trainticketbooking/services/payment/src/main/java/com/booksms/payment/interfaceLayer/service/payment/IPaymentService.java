package com.booksms.payment.interfaceLayer.service.payment;

import com.booksms.payment.interfaceLayer.dto.PaymentDTO;

public interface IPaymentService {

    void saveToRedis(Long orderNumber, PaymentDTO paymentDTO);

    PaymentDTO save(Long orderNumber);
    PaymentDTO save(PaymentDTO paymentDTO);
}
