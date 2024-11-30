package com.bookms.order.infrastructure.serviceGateway.impl;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.infrastructure.FeignClient.PaymentMomoClient;
import com.bookms.order.infrastructure.serviceGateway.IPaymentMethodService;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Qualifier("MOMO")
@RequiredArgsConstructor
public class MomoService implements IPaymentMethodService {
    private final PaymentMomoClient paymentMomoClient;


    @Override
    public ResponseEntity<ResponseDTO> create(PaymentModel paymentModel) {
        return paymentMomoClient.createByMomo(paymentModel);
    }
}
