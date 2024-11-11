package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {
    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);
    @Autowired
    private BankService bankService;
    @Autowired
    private PaymentRepository paymentRepository;
    @RabbitListener(queues = "paymentQueue")
    public void handlePayment(BankPaymentRequest request) {
        System.out.println("Payment request is handled: " + request);

        BankPaymentResponse response = bankService.pay(request);

        Payment payment = new Payment();
        payment.setBankResponse(response.getResultCode());
        payment.setPrice(request.getPrice());
        paymentRepository.save(payment);
        logger.info("Payment saved successfully!");
    }
}
