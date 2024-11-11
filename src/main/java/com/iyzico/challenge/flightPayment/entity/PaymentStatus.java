package com.iyzico.challenge.flightPayment.entity;

public enum PaymentStatus {
    UNPAID("UNPAID"),
    PAID("PAID")
    ;

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return status;
    }
}
