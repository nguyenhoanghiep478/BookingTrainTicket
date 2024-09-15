package com.bookms.order.infrastructure.serviceGateway.impl;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.infrastructure.FeignClient.PaymentPaypalClient;
import com.bookms.order.infrastructure.serviceGateway.IPaymentMethodService;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Qualifier("PAYPAL")
public class PaypalPayment implements IPaymentMethodService {
    private final PaymentPaypalClient client;


    @Override
    public ResponseEntity<ResponseDTO> create(PaymentModel paymentModel) {
        return client.createByPaypal(paymentModel);
    }
}
