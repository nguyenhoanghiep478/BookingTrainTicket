package com.booksms.payment.interfaceLayer.service.payment;

import com.booksms.payment.application.model.PaymentModel;
import com.booksms.payment.interfaceLayer.dto.PaymentDTO;

public interface ICreatePaymentService {

   void saveToRedis(Long id, PaymentDTO paymentDTO);

   PaymentModel save(Long orderNumber);
   PaymentModel save(PaymentDTO paymentDTO);
}
