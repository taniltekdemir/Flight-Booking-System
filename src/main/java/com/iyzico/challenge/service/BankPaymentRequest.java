package com.iyzico.challenge.service;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankPaymentRequest implements Serializable {

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
