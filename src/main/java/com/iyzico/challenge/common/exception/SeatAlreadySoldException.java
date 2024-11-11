package com.iyzico.challenge.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SeatAlreadySoldException extends RuntimeException {
    public SeatAlreadySoldException(String message) {
        super(message);
    }
}