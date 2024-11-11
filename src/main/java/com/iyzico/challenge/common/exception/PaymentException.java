package com.iyzico.challenge.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PaymentException extends RuntimeException{
    public PaymentException(String s, String concat) {
        super(s);
    }
}