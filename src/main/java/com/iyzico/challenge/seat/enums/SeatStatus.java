package com.iyzico.challenge.seat.enums;

public enum SeatStatus {
    AVAILABLE("AVAILABLE"),

    SOLD("SOLD"),
    SUSPEND("SUSPEND"),
    UNAVAILABLE("UNAVAILABLE")
    ;

    private String status;

    SeatStatus(String status) {
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