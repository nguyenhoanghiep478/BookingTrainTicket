package com.booksms.payment.web.controller;

import com.booksms.payment.interfaceLayer.dto.PaymentDTO;
import com.booksms.payment.interfaceLayer.dto.ResponsePayment;
import com.booksms.payment.interfaceLayer.service.payment.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.booksms.payment.application.model.Status.PROCESSING;

@Controller
@RequestMapping("/api/v1/payment/cod")
@RequiredArgsConstructor
public class CodController {
    private final IPaymentService paymentService;
    private final KafkaTemplate<String, ResponsePayment> kafkaTemplate;
    private final ModelMapper modelMapper;


    @PostMapping("/create")
    public String createPayment(@RequestBody PaymentDTO payment) {
        PaymentDTO response = paymentService.save(payment);
        ResponsePayment responsePayment = modelMapper.map(response,ResponsePayment.class);
        responsePayment.setStatus(PROCESSING);
        kafkaTemplate.send("payment-response",responsePayment);
        return "paymentSuccess";
    }
}
