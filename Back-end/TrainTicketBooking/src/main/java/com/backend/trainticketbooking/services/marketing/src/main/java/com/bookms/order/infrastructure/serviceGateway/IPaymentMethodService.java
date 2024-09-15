package com.bookms.order.infrastructure.serviceGateway;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IPaymentMethodService {
    ResponseEntity<ResponseDTO> create(PaymentModel paymentModel);
}
