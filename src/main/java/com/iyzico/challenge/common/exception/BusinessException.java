package com.iyzico.challenge.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
    }
}
