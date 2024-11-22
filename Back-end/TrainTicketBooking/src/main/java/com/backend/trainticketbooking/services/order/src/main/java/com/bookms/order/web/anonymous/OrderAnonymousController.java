package com.bookms.order.web.anonymous;

import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.application.servicegateway.IPaymentServiceGateway;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import com.bookms.order.interfaceLayer.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/order/anonymous")
@RequiredArgsConstructor
public class OrderAnonymousController {
    private final IOrderService service;
    private final IPaymentServiceGateway paymentServiceGateway;
    @PostMapping("/pay-order")
    public ResponseEntity<?> PayOrder(@RequestBody OrderDTO request) {
        PaymentModel paymentModel = service.prePay(request);
        if (paymentModel == null) {
            return ResponseEntity.ok(ResponseDTO.builder()
                    .message(Arrays.asList("sent token"))
                    .status(200)
                    .result(null)
                    .build()
            );
        }
        return paymentServiceGateway.create(paymentModel);
    }
}
