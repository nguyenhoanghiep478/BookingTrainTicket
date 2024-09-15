package com.booksms.payment.interfaceLayer.service.payment.impl;

import com.booksms.payment.application.model.PaymentModel;
import com.booksms.payment.application.usecase.CreatePaymentUseCase;
import com.booksms.payment.core.domain.entity.Payment;
import com.booksms.payment.interfaceLayer.dto.PaymentDTO;
import com.booksms.payment.interfaceLayer.service.RedisService.PaypalRedisService;
import com.booksms.payment.interfaceLayer.service.payment.ICreatePaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePaymentService implements ICreatePaymentService {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final PaypalRedisService paypalRedisService;
    private final ModelMapper modelMapper;


    @Override
    public void saveToRedis(Long id, PaymentDTO paymentDTO) {
        paypalRedisService.setPayment(id,PaymentModel.builder()
                        .orderNumber(paymentDTO.getOrderNumber())
                        .paymentMethod(paymentDTO.getMethod())
                        .totalAmount(paymentDTO.getTotal())
                .build());
    }

    @Override
    public PaymentModel save(Long paymentId) {
        PaymentModel paymentModel = paypalRedisService.getPayment(paymentId);
        PaymentModel result = createPaymentUseCase.execute(paymentModel);
        paypalRedisService.removePayment(paymentId);
        return result;
    }

    @Override
    public PaymentModel save(PaymentDTO paymentDTO) {
        PaymentModel paymentModel = modelMapper.map(paymentDTO, PaymentModel.class);
        return createPaymentUseCase.execute(paymentModel);
    }
}
