package com.booksms.payment.application.usecase;

import com.booksms.payment.application.model.PaymentModel;
import com.booksms.payment.core.domain.entity.Payment;
import com.booksms.payment.core.domain.exception.CreatePaymentFailedException;
import com.booksms.payment.core.domain.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreatePaymentUseCase implements BaseUseCase<PaymentModel,PaymentModel>{
    private final IPaymentRepository paymentRepository;
    @Override
    @Transactional(rollbackFor = CreatePaymentFailedException.class)
    public PaymentModel execute(PaymentModel paymentModel) {
        Payment payment = toPayment(paymentModel);
        Payment result = paymentRepository.save(payment).orElseThrow(
                () -> new CreatePaymentFailedException("Payment not created")
        );
        paymentModel.setId(result.getId());
        return paymentModel;
    }

    private Payment toPayment(PaymentModel paymentModel) {
        Payment payment = new Payment();
        payment.setOrderNumber(paymentModel.getOrderNumber());
        payment.setPaymentMethod(paymentModel.getPaymentMethod());
        payment.setTotalAmount(paymentModel.getTotalAmount());

        return payment;
    }

}
