package com.bookms.order.application.servicegateway;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IPaymentServiceGateway {
    ResponseEntity<ResponseDTO> create(PaymentModel paymentModel);
}
