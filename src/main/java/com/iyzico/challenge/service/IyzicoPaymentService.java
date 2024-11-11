package com.iyzico.challenge.service;

//import com.iyzico.challenge.configuration.RabbitMQConfig;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class IyzicoPaymentService {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);

    private BankService bankService;
    private PaymentRepository paymentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
    }

//    public void pay(BigDecimal price) {
//        //pay with bank
//        BankPaymentRequest request = new BankPaymentRequest();
//        request.setPrice(price);
//        BankPaymentResponse response = bankService.pay(request);
//
//        //insert records
//        Payment payment = new Payment();
//        payment.setBankResponse(response.getResultCode());
//        payment.setPrice(price);
//        paymentRepository.save(payment);
//        logger.info("Payment saved successfully!");
//    }

    public void pay(BigDecimal price) {

        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        rabbitTemplate.convertAndSend("paymentQueue", request);
        System.out.println("Payment request sen to queue: " + request);


    }
}
