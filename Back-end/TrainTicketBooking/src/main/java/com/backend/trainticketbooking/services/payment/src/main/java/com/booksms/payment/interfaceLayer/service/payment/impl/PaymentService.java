package com.booksms.payment.interfaceLayer.service.payment.impl;

import com.booksms.payment.application.model.PaymentModel;
import com.booksms.payment.interfaceLayer.dto.PaymentDTO;
import com.booksms.payment.interfaceLayer.service.payment.ICreatePaymentService;
import com.booksms.payment.interfaceLayer.service.payment.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService implements IPaymentService {
    private final ICreatePaymentService createPaymentService;
    private final ModelMapper modelMapper;

    @Override
    public void saveToRedis(Long id, PaymentDTO paymentDTO) {
        createPaymentService.saveToRedis(id,paymentDTO);
    }

    @Override
    public PaymentDTO save(Long orderNumber) {
      PaymentModel payment = createPaymentService.save(orderNumber);
      return PaymentDTO.builder()
              .id(payment.getId())
              .method(payment.getPaymentMethod())
              .orderNumber(payment.getOrderNumber())
              .build();
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        PaymentModel payment = createPaymentService.save(paymentDTO);
        return modelMapper.map(payment, PaymentDTO.class);
    }
}
