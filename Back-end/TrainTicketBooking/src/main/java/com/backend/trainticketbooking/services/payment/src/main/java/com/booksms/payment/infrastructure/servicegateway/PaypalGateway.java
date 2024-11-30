package com.booksms.payment.infrastructure.servicegateway;

import com.booksms.payment.application.model.PaypalCustomModel;
import com.booksms.payment.interfaceLayer.dto.ResponsePayment;
import com.booksms.payment.interfaceLayer.service.RedisService.OrderRedisService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalGateway {
    private final APIContext apiContext;
    private final OrderRedisService orderRedisService;
    public Payment createPayment(PaypalCustomModel paypalCustomModel, ResponsePayment responsePayment) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(paypalCustomModel.getCurrency());
        amount.setTotal(String.format(Locale.forLanguageTag(paypalCustomModel.getCurrency()),"%.2f",paypalCustomModel.getTotal()));

        Transaction transaction = new Transaction();
        transaction.setDescription(paypalCustomModel.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        paypalCustomModel.setOrderNumber(responsePayment.getOrderNumber());
        Payment payment = getPayment(paypalCustomModel, transactions);

        Payment createdPayment = payment.create(apiContext);
        responsePayment.setPaymentMethod("PAYPAL");
        orderRedisService.setValue(String.valueOf(responsePayment.getOrderNumber()),responsePayment);
        return createdPayment;
    }

    public Payment excutePayment(String paymentId,String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);

        return payment.execute(apiContext, execution);
    }

    private Payment getPayment(PaypalCustomModel paypalCustomModel, List<Transaction> transactions) {
        Payer payer = new Payer();
        payer.setPaymentMethod(paypalCustomModel.getMethod());

        Payment payment = new Payment();
        payment.setIntent(paypalCustomModel.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);




        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paypalCustomModel.getCancelUrl());
        redirectUrls.setReturnUrl(paypalCustomModel.getReturnUrl());

        payment.setRedirectUrls(redirectUrls);
        return payment;
    }
}
