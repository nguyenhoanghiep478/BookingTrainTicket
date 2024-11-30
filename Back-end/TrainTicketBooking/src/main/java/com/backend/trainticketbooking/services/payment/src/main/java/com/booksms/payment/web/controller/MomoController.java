package com.booksms.payment.web.controller;

import com.booksms.payment.application.model.ResponsePaymentPageModel;
import com.booksms.payment.application.model.Status;
import com.booksms.payment.infrastructure.servicegateway.MomoGateway;
import com.booksms.payment.interfaceLayer.dto.*;
import com.booksms.payment.interfaceLayer.service.RedisService.OrderRedisService;
import com.booksms.payment.interfaceLayer.service.payment.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/api/v1/payment/momo")
@RequiredArgsConstructor
@Slf4j
public class MomoController {
    private final MomoGateway momoPaymentService;
    private final OrderRedisService orderRedisService;
    private final IPaymentService paymentService;
    private final KafkaTemplate<String,ResponsePayment> kafkaTemplate;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            Random random = new Random();
            MomoPaymentResponse response = momoPaymentService.createPayment(paymentDTO,ResponsePayment.builder()
                            .orderNumber(paymentDTO.getOrderNumber())
                            .paymentId(random.nextInt(0,100000000))
                            .paymentMethod(paymentDTO.getPaymentMethod())
                    .build());
            return ResponseEntity.ok(ResponseDTO.builder()
                    .status(200)
                    .message(List.of("created"))
                    .result(ResponsePaymentPageModel.builder()
                            .status("created")
                            .message(response.getPayUrl())
                            .build())
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(500)
                .message(List.of("failed"))
                .result(ResponsePaymentPageModel.builder()
                        .status("failed")
                        .message("http://localhost:5558/api/v1/payment/momo/error")
                        .build())
                .build());
    }

    @PostMapping("/ipn")
    public String ipnPayment(@RequestBody IpnRequest request) {
        log.info(request.toString());
        Boolean responseFromMomo = momoPaymentService.handleResponseIpn(request);
        if(responseFromMomo){
            return paymentSuccess(request.getOrderId(), String.valueOf(request.getResultCode()));
        }else{
            return paymentError();
        }
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam("orderId") String orderId,
            @RequestParam("resultCode") String resultCode
    ) {
        try {
            ResponsePayment responsePayment = orderRedisService.getValue(orderId);
            log.info(responsePayment.toString());
            if ("0".equals(resultCode)) {
                responsePayment.setStatus(Status.COMPLETED);

                PaymentDTO paymentDTO = paymentService.save(responsePayment.getOrderNumber());
                responsePayment.setPaymentId(paymentDTO.getId());

                kafkaTemplate.send("payment-response", responsePayment);

                orderRedisService.deleteValue(orderId);
                return "paymentSuccess";
            } else {
                log.error("Payment failed for orderId: {}", orderId);
                return paymentCancel(orderId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/cancel")
    public String paymentCancel(
            @RequestParam("orderId") String orderId
    ) {
        ResponsePayment responsePayment = orderRedisService.getValue(orderId);
        responsePayment.setStatus(Status.CANCELLED);

        kafkaTemplate.send("payment-response", responsePayment);

        orderRedisService.deleteValue(orderId);
        return "paymentCancel";
    }

    @GetMapping("/error")
    public String paymentError() {
        return "paymentError";
    }

}
