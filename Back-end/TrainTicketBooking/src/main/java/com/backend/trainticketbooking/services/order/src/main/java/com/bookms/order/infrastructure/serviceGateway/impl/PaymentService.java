package com.bookms.order.infrastructure.serviceGateway.impl;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.application.servicegateway.IPaymentServiceGateway;
import com.bookms.order.infrastructure.serviceGateway.IPaymentMethodService;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.bookms.order.core.domain.State.PaymentMethod.COD;
import static com.bookms.order.core.domain.State.PaymentMethod.PAYPAL;

@Component
public class PaymentService implements IPaymentServiceGateway{
    private final IPaymentMethodService paypalService;
    private final IPaymentMethodService codService;
    public PaymentService(
            @Qualifier("PAYPAL") IPaymentMethodService paypalService,
            @Qualifier("COD") IPaymentMethodService codService
    ) {
        this.paypalService = paypalService;
        this.codService = codService;
    }


    @Override
    public ResponseEntity<ResponseDTO> create(PaymentModel paymentModel) {
        if(paymentModel.getMethod().toUpperCase().equals(COD.getValue())){
            return codService.create(paymentModel);
        }else if(paymentModel.getMethod().toUpperCase().equals(PAYPAL.getValue())){
            return paypalService.create(paymentModel);
        }
        return null;
    }
}
